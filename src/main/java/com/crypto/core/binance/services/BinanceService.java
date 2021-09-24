package com.crypto.core.binance.services;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;

import java.util.List;

public interface BinanceService {
    void subscribe(String requesterId, String symbolName, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    void unsubscribe(String requesterId, String symbolName);
    SymbolPrice getSymbolPrice(String symbolName);
    List<SymbolPrice> getSymbolPrices(String... symbolNames);
}
