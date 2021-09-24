package com.crypto.core.binance.client.impl;

import com.crypto.core.binance.client.SubscriptionClient;
import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.SubscriptionOptions;
import com.crypto.core.binance.client.domain.user.UserDataUpdateEvent;
import com.crypto.core.binance.client.domain.enums.CandlestickInterval;
import com.crypto.core.binance.client.domain.event.AggregateTradeEvent;
import com.crypto.core.binance.client.domain.event.CandlestickEvent;
import com.crypto.core.binance.client.domain.event.LiquidationOrderEvent;
import com.crypto.core.binance.client.domain.event.MarkPriceEvent;
import com.crypto.core.binance.client.domain.event.OrderBookEvent;
import com.crypto.core.binance.client.domain.event.SymbolBookTickerEvent;
import com.crypto.core.binance.client.domain.event.SymbolMiniTickerEvent;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;

import java.util.*;

public class WebSocketStreamClientImpl implements SubscriptionClient {
    private final SubscriptionOptions options;
    private final WebsocketRequestImpl requestImpl;
    private final Map<String, WebSocketConnection> connections = new LinkedHashMap<>();
    private WebSocketWatchDog watchDog;

    WebSocketStreamClientImpl(SubscriptionOptions options) {
        this.watchDog = null;
        this.options = Objects.requireNonNull(options);
        this.requestImpl = new WebsocketRequestImpl();
    }

    private <T> void createConnection(String requesterId, WebsocketRequest<T> request, boolean autoClose) {
        if (watchDog == null) {
            watchDog = new WebSocketWatchDog(options);
        }
        WebSocketConnection connection = new WebSocketConnection(request, watchDog, autoClose);
        if (autoClose == false) {
            connections.put(requesterId, connection);
        }
        connection.connect();
    }

    private <T> void createConnection(String requesterId, WebsocketRequest<T> request) {
        createConnection(requesterId, request, false);
    }

    @Override
    public void unsubscribe(String requesterId, String symbolName) {
        WebSocketConnection connection = connections.remove(requesterId);
        if (connection != null) {
            watchDog.onClosedNormally(connection);
            connection.close();
        }
    }

    @Override
    public void unsubscribeAll() {
        for (WebSocketConnection connection : connections.values()) {
            watchDog.onClosedNormally(connection);
            connection.close();
        }
        connections.clear();
    }

    @Override
    public void subscribeAggregateTradeEvent(
            String requesterId,
            String symbolName,
            SubscriptionListener<AggregateTradeEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(requesterId, requestImpl.subscribeAggregateTradeEvent(symbolName, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeMarkPriceEvent(
            String requesterId,
            String symbolName,
            SubscriptionListener<MarkPriceEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(requesterId, requestImpl.subscribeMarkPriceEvent(symbolName, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeCandlestickEvent(
            String requesterId,
            String symbolName, CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeCandlestickEvent(symbolName, interval, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolMiniTickerEvent(
            String requesterId,
            String symbolName,
            SubscriptionListener<SymbolMiniTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeSymbolMiniTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllMiniTickerEvent(
            String requesterId,
            String symbolName,
            SubscriptionListener<List<SymbolMiniTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeAllMiniTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolTickerEvent(
            String requesterId,
            String symbolName,
            SubscriptionListener<SymbolTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeSymbolTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllTickerEvent(
            String requesterId,
            SubscriptionListener<List<SymbolTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeAllTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolBookTickerEvent(
            String requesterId,
            String symbolName, SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeSymbolBookTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllBookTickerEvent(
            String requesterId,
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeAllBookTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolLiquidationOrderEvent(
            String requesterId,
            String symbolName, SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeSymbolLiquidationOrderEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllLiquidationOrderEvent(
            String requesterId,
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeAllLiquidationOrderEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeBookDepthEvent(
            String requesterId,
            String symbolName,
            Integer limit,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeBookDepthEvent(symbolName, limit, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeDiffDepthEvent(
            String participantId,
            String symbolName,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeDiffDepthEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeUserDataEvent(
            String requesterId,
            String listenKey,
            SubscriptionListener<UserDataUpdateEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                requesterId,
                requestImpl.subscribeUserDataEvent(listenKey, subscriptionListener, errorHandler)
        );
    }
}
