package com.crypto.cli.binance.services;

import com.crypto.cli.binance.client.domain.market.SymbolPrice;
import com.crypto.cli.binance.client.SubscriptionErrorHandler;
import com.crypto.cli.binance.client.SubscriptionListener;
import com.crypto.cli.binance.client.domain.event.SymbolMiniTickerEvent;
import com.crypto.cli.binance.client.domain.event.SymbolTickerEvent;

import java.util.List;

public interface BinanceService {
    void unsubscribe(Integer participantId, String symbolName);
    void unsubscribeAll();
    void subscribeSymbolTickerEvent(Integer participantId, String symbol, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    void subscribeSymbolMiniTickerEvent(Integer participantId, String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler);
    List<SymbolPrice> getSymbolPrices(String symbols);
}
