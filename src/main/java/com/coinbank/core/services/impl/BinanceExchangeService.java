package com.coinbank.core.services.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.event.TickerEvent;
import com.coinbank.core.configs.BinanceProperties;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.domain.User;
import com.coinbank.core.dto.AssetDto;
import com.coinbank.core.enums.NotificationType;
import com.coinbank.core.enums.Quotation;
import com.coinbank.core.exceptions.AssetNotFoundException;
import com.coinbank.core.services.ApiRestClient;
import com.coinbank.core.services.ApiWebSocketClient;
import com.coinbank.core.services.ExchangeService;
import com.coinbank.core.services.NotificationTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service("BINANCE")
public class BinanceExchangeService implements ExchangeService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceExchangeService.class);
    //TODO: Must be closeable per user asset pair
    private static final Map<String, Closeable> events = new HashMap<>();

    private final ApiRestClient apiRestClient;
    private final ApiWebSocketClient apiWebSocketClient;
    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final NotificationTemplate notificationTemplate;

    public BinanceExchangeService(
            ApiRestClient apiRestClient,
            ApiWebSocketClient apiWebSocketClient,
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            NotificationTemplate notificationTemplate
    ) {
        this.apiRestClient = apiRestClient;
        this.apiWebSocketClient = apiWebSocketClient;
        this.conversionService = conversionService;
        this.binanceProperties = binanceProperties;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    public void createAssetTicker(User user, String assetName) {
        findAssetByName(user, assetName)
                .map(asset -> {
                    events.computeIfAbsent(assetName, (String name) ->
                            apiWebSocketClient.onTickerEvent(
                                    asset.getAsset(),
                                    (TickerEvent tickerEvent) -> {
                                        try {
                                            sendNotification(user, asset, tickerEvent);
                                        } catch (Exception e) {
                                            LOG.error(e.getMessage(), e);
                                        }
                                    }
                            ));
                    return asset;
                })
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }

    @Override
    public void removeAssetTicker(String assetName) {
        Closeable tickerEvent = events.remove(assetName);
        if (tickerEvent != null) {
            try {
                tickerEvent.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        return apiRestClient.listOhlc(assetName, interval, start, end);
    }

    @Override
    public List<Asset> listAssets(User user, Set<String> assets) {
        return apiRestClient.listAssets().stream()
                .filter(this::onlyAllowedAssets)
                .filter(asset -> assets.contains(asset.getAsset()))
                .map((Asset asset) -> {
                    asset.setPriceChange(BigDecimal.ZERO);
                    apiRestClient.getPrice(asset.getAsset())
                            .map(assetPrice ->
                                    updateAsset(asset, assetPrice.getPrice())
                            )
                            .orElseGet(() ->
                                    updateAsset(asset, BigDecimal.ONE)
                            );
                    return asset;
                })
                .sorted(Comparator.comparing(Asset::getFree).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> availableAssets() {
        return binanceProperties.getAssets().keySet();
    }

    @Override
    public Optional<Asset> findAssetByName(User user, String assetName) {
        return apiRestClient.listAssets().stream()
                .filter(asset -> asset.getAsset().equals(assetName))
                .map(asset -> {
                    asset.setPriceChange(BigDecimal.ZERO);
                    return apiRestClient.getPrice(asset.getAsset())
                            .map(assetPrice ->
                                    updateAsset(asset, assetPrice.getPrice())
                            )
                            .orElseGet(() ->
                                    updateAsset(asset, BigDecimal.ONE)
                            );
                })
                .findFirst();
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        return apiRestClient.getPrice(assetName);
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                "user.getApiKey()",
                "user.getSecretKey()",
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiRestClient(conversionService, binanceProperties, factory.newRestClient());
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                "user.getApiKey()",
                "user.getSecretKey()",
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiWebSocketClient(binanceProperties, factory.newWebSocketClient());
    }


    private void sendNotification(User user, Asset asset, TickerEvent tickerEvent) {
        asset.setPriceChange(new BigDecimal(tickerEvent.getPriceChangePercent()));
        asset = updateAsset(asset, new BigDecimal(tickerEvent.getBestBidPrice()));
        AssetDto assetDto = toAssetDto(asset);
        notificationTemplate.sendNotification(user, NotificationType.TICKER_EVENT, assetDto);
    }

    private Asset updateAsset(Asset asset, BigDecimal price) {
        asset.setQuotation(
                asset.getPrice() == null
                        ? Quotation.EQUAL
                        : Quotation.valueOf(price.compareTo(asset.getPrice()))
        );
        asset.setPrice(price);
        BigDecimal balance = BigDecimal.ZERO;
        if (asset.getFree().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    asset.getFree()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        if (asset.getLocked().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    asset.getLocked()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        asset.setBalance(balance);
        return asset;
    }

    private boolean onlyAllowedAssets(Asset asset) {
        return !binanceProperties.getBlacklist().contains(asset.getAsset());
    }

    private AssetDto toAssetDto(Asset asset) {
        return conversionService.convert(asset, AssetDto.class);
    }
}
