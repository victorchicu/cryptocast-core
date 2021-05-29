package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.binance.SubscriptionErrorHandler;
import com.cryptostrophe.bot.binance.SubscriptionListener;
import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;

import java.util.List;

public interface BinanceService {
    void unsubscribeAll();
    void subscribeSymbolMiniTickerEvent(String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    List<SymbolPrice> getSymbolPrices(String symbols);
}
