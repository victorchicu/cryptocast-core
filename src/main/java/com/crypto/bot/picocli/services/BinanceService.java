package com.crypto.bot.picocli.services;

import com.crypto.bot.binance.model.market.SymbolPrice;
import com.crypto.bot.binance.SubscriptionErrorHandler;
import com.crypto.bot.binance.SubscriptionListener;
import com.crypto.bot.binance.model.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.model.event.SymbolTickerEvent;

import java.util.List;

public interface BinanceService {
    void unsubscribeAll();
    void subscribeSymbolTickerEvent(String symbol, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    void subscribeSymbolMiniTickerEvent(String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    List<SymbolPrice> getSymbolPrices(String symbols);
}
