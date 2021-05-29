package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.binance.SubscriptionClient;
import com.cryptostrophe.bot.binance.SubscriptionErrorHandler;
import com.cryptostrophe.bot.binance.SubscriptionListener;
import com.cryptostrophe.bot.binance.SyncRequestClient;
import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.services.BinanceService;
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

    public void subscribeSymbolMiniTickerEvent(String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler) {
        subscriptionClient.subscribeSymbolMiniTickerEvent(symbol, callback, errorHandler);
    }
}
