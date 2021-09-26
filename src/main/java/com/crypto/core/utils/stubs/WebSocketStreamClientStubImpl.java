package com.crypto.core.utils.stubs;

import com.crypto.core.exchanges.binance.client.SubscriptionClient;
import com.crypto.core.exchanges.binance.client.SubscriptionErrorHandler;
import com.crypto.core.exchanges.binance.client.SubscriptionListener;
import com.crypto.core.exchanges.binance.client.domain.enums.CandlestickInterval;
import com.crypto.core.exchanges.binance.client.domain.event.*;
import com.crypto.core.exchanges.binance.client.domain.user.UserDataUpdateEvent;

import java.util.List;

public class WebSocketStreamClientStubImpl implements SubscriptionClient {
    @Override
    public void unsubscribe(String requesterId, String symbolName) {

    }

    @Override
    public void unsubscribeAll() {

    }

    @Override
    public void subscribeAggregateTradeEvent(String requesterId, String symbolName, SubscriptionListener<AggregateTradeEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeMarkPriceEvent(String requesterId, String symbolName, SubscriptionListener<MarkPriceEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeCandlestickEvent(String requesterId, String symbolName, CandlestickInterval interval, SubscriptionListener<CandlestickEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolMiniTickerEvent(String requesterId, String symbolName, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllMiniTickerEvent(String requesterId, String symbolName, SubscriptionListener<List<SymbolMiniTickerEvent>> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolTickerEvent(String requesterId, String symbolName, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllTickerEvent(String requesterId, SubscriptionListener<List<SymbolTickerEvent>> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolBookTickerEvent(String requesterId, String symbolName, SubscriptionListener<SymbolBookTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllBookTickerEvent(String requesterId, SubscriptionListener<SymbolBookTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolLiquidationOrderEvent(String requesterId, String symbolName, SubscriptionListener<LiquidationOrderEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllLiquidationOrderEvent(String requesterId, SubscriptionListener<LiquidationOrderEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeBookDepthEvent(String requesterId, String symbolName, Integer limit, SubscriptionListener<OrderBookEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeDiffDepthEvent(String participantId, String symbolName, SubscriptionListener<OrderBookEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeUserDataEvent(String requesterId, String listenKey, SubscriptionListener<UserDataUpdateEvent> callback, SubscriptionErrorHandler errorHandler) {

    }
}
