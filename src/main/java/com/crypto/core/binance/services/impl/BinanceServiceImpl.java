package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.account.AssetBalance;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.exceptions.AssetNotFoundException;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.services.SubscriptionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BinanceServiceImpl implements BinanceService {
    private static final long DEFAULT_RECEIVE_WINDOW = 30000L;
    private static final Map<String, Closeable> tickerEvents = new HashMap<>();

    private final SubscriptionService subscriptionService;
    private final BinanceProperties binanceProperties;
    private final ConversionService conversionService;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceServiceImpl(
            SubscriptionService subscriptionService,
            BinanceProperties binanceProperties,
            ConversionService conversionService,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.subscriptionService = subscriptionService;
        this.binanceProperties = binanceProperties;
        this.conversionService = conversionService;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void addTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback) {
        String symbolName = getSymbol(assetName);
        tickerEvents.put(assetName, binanceApiWebSocketClient.onTickerEvent(symbolName, callback));
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
                .filter(upBalance())
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

    private Predicate<AssetBalance> upBalance() {
        return assetBalance -> !binanceProperties.getBlacklist().contains(assetBalance.getAsset()) && assetBalance.getFree().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public Optional<AssetBalance> findAssetByName(Principal principal, String assetName) {
        return listAssets(principal).stream()
                .filter(asset -> asset.getAsset().equals(assetName))
                .findFirst();
    }


    private String getSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.Asset::getCoin)
                .map(String::toLowerCase)
                .orElse(assetName);
    }

    private BinanceProperties.Asset getAsset(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(AssetNotFoundException::new);
    }
}
