package com.crypto.bot.binance.services;

import com.crypto.bot.binance.api.domain.market.SymbolPrice;
import com.crypto.bot.binance.api.SubscriptionErrorHandler;
import com.crypto.bot.binance.api.SubscriptionListener;
import com.crypto.bot.binance.api.domain.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.api.domain.event.SymbolTickerEvent;

import java.util.List;

public interface BinanceService {
    void unsubscribeAll();
    void subscribeSymbolTickerEvent(String symbol, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    void subscribeSymbolMiniTickerEvent(String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    List<SymbolPrice> getSymbolPrices(String symbols);
}
