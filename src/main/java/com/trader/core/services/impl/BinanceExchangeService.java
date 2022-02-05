package com.trader.core.services.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.clients.impl.ExtendedBinanceApiRestClient;
import com.trader.core.clients.impl.ExtendedBinanceApiWebSocketClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.User;
import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.enums.NotificationType;
import com.trader.core.enums.Quotation;
import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.NotificationTemplate;
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
                .map(assetBalance -> {
                    events.computeIfAbsent(assetName, (String name) ->
                            apiWebSocketClient.onTickerEvent(
                                    assetBalance.getAsset(),
                                    tickerEvent -> {
                                        try {
                                            LOG.info(tickerEvent.toString());
                                            sendNotification(user, assetBalance, tickerEvent);
                                        } catch (Exception e) {
                                            LOG.error(e.getMessage(), e);
                                        }
                                    }
                            ));
                    return assetBalance;
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
    public Set<String> listAssets() {
        return binanceProperties.getAssets().keySet();
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                user.getApiKey(),
                user.getSecretKey(),
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiRestClient(binanceProperties, factory.newRestClient(), conversionService);
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                user.getApiKey(),
                user.getSecretKey(),
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiWebSocketClient(binanceProperties, factory.newWebSocketClient());
    }

    @Override
    public List<AssetBalance> listAssetsBalances(User user) {
        return apiRestClient.getAssetsBalances().stream()
                .filter(this::onlyAllowedAssets)
                .filter(this::onlyEffectiveAssetBalance)
                .map((AssetBalance assetBalance) -> {
                    TickerPrice tickerPrice = apiRestClient.getPrice(assetBalance.getAsset());
                    return updateAssetBalance(assetBalance, new BigDecimal(tickerPrice.getPrice()));
                })
                .sorted(Comparator.comparing(AssetBalance::getFree).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AssetBalance> findAssetByName(User user, String assetName) {
        return apiRestClient.getAssetsBalances().stream()
                .filter(assetBalance -> assetBalance.getAsset().equals(assetName))
                .findFirst();
    }


    private void sendNotification(User user, AssetBalance assetBalance, TickerEvent tickerEvent) {
        assetBalance = updateAssetBalance(assetBalance, new BigDecimal(tickerEvent.getCurrentDaysClosePrice()));
        AssetBalanceDto assetBalanceDto = toAssetBalanceDto(assetBalance);
        notificationTemplate.sendNotification(user, NotificationType.TICKER_EVENT, assetBalanceDto);
    }

    private boolean onlyAllowedAssets(AssetBalance assetBalance) {
        return !binanceProperties.getBlacklist().contains(assetBalance.getAsset());
    }

    private boolean onlyEffectiveAssetBalance(AssetBalance assetBalance) {
        return assetBalance.getFree().compareTo(BigDecimal.ZERO) > 0 || assetBalance.getLocked().compareTo(BigDecimal.ZERO) > 0;
    }

    private AssetBalance updateAssetBalance(AssetBalance assetBalance, BigDecimal price) {
        assetBalance.setQuotation(
                assetBalance.getPrice() == null
                        ? Quotation.EQUAL
                        : Quotation.valueOf(price.compareTo(assetBalance.getPrice()))
        );
        assetBalance.setPrice(price);
        BigDecimal balance = BigDecimal.ZERO;
        if (assetBalance.getFree().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    assetBalance.getFree()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        if (assetBalance.getLocked().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    assetBalance.getLocked()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        assetBalance.setBalance(balance);
        return assetBalance;
    }

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
