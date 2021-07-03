package com.crypto.bot.binance.services.impl;

import com.crypto.bot.binance.client.domain.market.SymbolPrice;
import com.crypto.bot.binance.client.SubscriptionClient;
import com.crypto.bot.binance.client.SubscriptionErrorHandler;
import com.crypto.bot.binance.client.SubscriptionListener;
import com.crypto.bot.binance.client.SyncRequestClient;
import com.crypto.bot.binance.client.domain.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.bot.binance.services.BinanceService;
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
    public void unsubscribe(Integer participantId) {
        subscriptionClient.unsubscribe(participantId);
    }

    @Override
    public void unsubscribeAll() {
        subscriptionClient.unsubscribeAll();
    }

    @Override
    public void subscribeSymbolTickerEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<SymbolTickerEvent> callback,
            SubscriptionErrorHandler errorHandler
    ) {
        subscriptionClient.subscribeSymbolTickerEvent(participantId, symbol, callback, errorHandler);
    }

    public void subscribeSymbolMiniTickerEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<SymbolMiniTickerEvent> callback,
            SubscriptionErrorHandler errorHandler
    ) {
        subscriptionClient.subscribeSymbolMiniTickerEvent(participantId, symbol, callback, errorHandler);
    }
}
