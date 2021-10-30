package com.trader.core.binance.assets.services.impl;

import com.trader.core.binance.assets.dto.AssetBalanceDto;
import com.trader.core.binance.assets.exceptions.AssetNotFoundException;
import com.trader.core.binance.assets.services.AssetService;
import com.trader.core.binance.client.BinanceApiRestClient;
import com.trader.core.binance.client.BinanceApiWebSocketClient;
import com.trader.core.binance.client.domain.account.AssetBalance;
import com.trader.core.binance.client.domain.event.TickerEvent;
import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.notifications.enums.NotificationType;
import com.trader.core.notifications.services.NotificationTemplate;
import com.trader.core.subscriptions.controllers.SubscriptionController;
import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    private static final long DEFAULT_RECEIVE_WINDOW = 30000L;
    private static final Map<String, Closeable> tickerEvents = new HashMap<>();

    private final BinanceProperties binanceProperties;
    private final ConversionService conversionService;
    private final SubscriptionService subscriptionService;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public AssetServiceImpl(
            BinanceProperties binanceProperties,
            ConversionService conversionService,
            SubscriptionService subscriptionService,
            NotificationTemplate notificationTemplate,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void addTickerEvent(Principal principal, String assetName) {
        findAssetByName(principal, assetName).ifPresentOrElse(assetBalance -> {
            BinanceProperties.Asset asset = getAsset(assetName);
            tickerEvents.put(assetName, binanceApiWebSocketClient.onTickerEvent(asset.getCoin().toLowerCase(), tickerEvent -> {
                try {
                    AssetBalanceDto assetDto = toAssetBalanceDto(asset, assetBalance, tickerEvent);
                    notificationTemplate.sendNotification(principal, NotificationType.TICKER_EVENT, assetDto);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }));
        }, AssetNotFoundException::new);
    }

    @Override
    public void removeTickerEvent(String assetName) {
        Closeable tickerEvent = tickerEvents.remove(assetName);
        if (tickerEvent != null) {
            try {
                tickerEvent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<AssetBalance> listAssets(Principal principal) {
        Page<Subscription> page = subscriptionService.findSubscriptions(principal, Pageable.unpaged());

        Set<String> assetNames = page.getContent().stream()
                .map(Subscription::getAssetName)
                .collect(Collectors.toSet());

        List<AssetBalance> assets = binanceApiRestClient.getAccount().getBalances().stream()
                .filter(hideSmallBalance())
                .sorted(Comparator.comparing(AssetBalance::getFree).reversed())
                .map((AssetBalance assetBalance) -> {
                    BinanceProperties.Asset asset = getAsset(assetBalance.getAsset());
                    assetBalance.setName(asset.getName());
                    assetBalance.setIcon(asset.getIcon());
                    assetBalance.setFlagged(assetNames.contains(assetBalance.getAsset()));
                    return assetBalance;
                })
                .collect(Collectors.toList());

        return assets;
    }

    @Override
    public Optional<AssetBalance> findAssetByName(Principal principal, String assetName) {
        return listAssets(principal).stream()
                .filter(asset -> asset.getAsset().equals(assetName))
                .findFirst();
    }

    private AssetBalanceDto toAssetBalanceDto(BinanceProperties.Asset asset, AssetBalance assetBalance, TickerEvent tickerEvent) {
        BigDecimal balance = assetBalance.getFree().setScale(8, RoundingMode.UNNECESSARY);
        BigDecimal usdtValue = assetBalance.getFree().multiply(tickerEvent.getWeightedAveragePrice()).setScale(8, RoundingMode.HALF_EVEN);
        return new AssetBalanceDto(assetBalance.getAsset(), assetBalance.getName(), asset.getIcon(), Boolean.TRUE, balance, usdtValue);
    }

    private Predicate<AssetBalance> hideSmallBalance() {
        return assetBalance -> !binanceProperties.getBlacklist().contains(assetBalance.getAsset()) && assetBalance.getFree().compareTo(BigDecimal.valueOf(0.00000002)) > 0 ;
    }

    private BinanceProperties.Asset getAsset(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(AssetNotFoundException::new);
    }
}
