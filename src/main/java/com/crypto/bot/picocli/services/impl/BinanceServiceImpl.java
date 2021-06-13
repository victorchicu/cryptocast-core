package com.crypto.bot.picocli.services.impl;

import com.crypto.bot.binance.model.market.SymbolPrice;
import com.crypto.bot.binance.SubscriptionClient;
import com.crypto.bot.binance.SubscriptionErrorHandler;
import com.crypto.bot.binance.SubscriptionListener;
import com.crypto.bot.binance.SyncRequestClient;
import com.crypto.bot.binance.model.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.model.event.SymbolTickerEvent;
import com.crypto.bot.picocli.services.BinanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceServiceImpl implements BinanceService {
    private final SyncRequestClient syncRequestClient;
    private final SubscriptionClient subscriptionClient;

    public BinanceServiceImpl(SyncRequestClient syncRequestClient, SubscriptionClient subscriptionClient) {
        this.syncRequestClient = syncRequestClient;
        this.subscriptionClient = subscriptionClient;
    }

    public List<SymbolPrice> getSymbolPrices(String symbols) {
        return syncRequestClient.getSymbolPriceTicker(symbols);
    }

    @Override
    public void unsubscribeAll() {
        subscriptionClient.unsubscribeAll();
    }

    @Override
    public void subscribeSymbolTickerEvent(String symbol, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler) {
        subscriptionClient.subscribeSymbolTickerEvent(symbol, callback, errorHandler);
    }

    public void subscribeSymbolMiniTickerEvent(String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler) {
        subscriptionClient.subscribeSymbolMiniTickerEvent(symbol, callback, errorHandler);
    }
}
