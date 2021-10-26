package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.OrderSide;
import com.crypto.core.binance.client.domain.OrderType;
import com.crypto.core.binance.client.domain.TimeInForce;
import com.crypto.core.binance.client.domain.account.NewOrder;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.subscriptions.services.SubscriptionService;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BinanceServiceImpl implements BinanceService {
    private static final long DEFAULT_RECEIVE_WINDOW = 30000L;
    private static final Map<String, Closeable> tickerEvents = new HashMap<>();

    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceServiceImpl(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void createOrder(String assetName) {
        binanceApiRestClient.newOrderTest(new NewOrder(assetName, OrderSide.SELL, OrderType.TAKE_PROFIT_LIMIT, TimeInForce.GTC, ""));
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
    public List<Asset> listAssets(Principal principal) {
        return binanceApiRestClient.listAssets(DEFAULT_RECEIVE_WINDOW, Instant.now().toEpochMilli()).stream()
                .filter(asset -> !binanceProperties.getBlacklist().contains(asset.getCoin()) && asset.getFree().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Asset::getFree).reversed())
                .collect(Collectors.toList());
    }


    private String getSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.Asset::getCoin)
                .map(String::toLowerCase)
                .orElse(assetName);
    }
}
