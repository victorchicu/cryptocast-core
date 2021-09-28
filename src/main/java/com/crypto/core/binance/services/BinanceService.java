package com.crypto.core.binance.services;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;

import java.security.Principal;
import java.util.List;

public interface BinanceService {
    void subscribe(Principal principal, String symbolName, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    void unsubscribe(Principal principal, String symbolName);
    SymbolPrice getSymbolPrice(String symbolName);
    List<SymbolPrice> getAllSymbolPrices();
}
