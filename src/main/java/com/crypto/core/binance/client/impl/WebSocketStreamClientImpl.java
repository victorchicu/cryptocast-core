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
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.*;

public class WebSocketStreamClientImpl implements SubscriptionClient {
    private final SubscriptionOptions options;
    private final WebsocketRequestImpl requestImpl;
    private final Map<Tuple2<Integer, String>, WebSocketConnection> connections = new LinkedHashMap<>();
    private WebSocketWatchDog watchDog;

    WebSocketStreamClientImpl(SubscriptionOptions options) {
        this.watchDog = null;
        this.options = Objects.requireNonNull(options);
        this.requestImpl = new WebsocketRequestImpl();
    }

    private <T> void createConnection(Integer participantId, String symbolName, WebsocketRequest<T> request, boolean autoClose) {
        if (watchDog == null) {
            watchDog = new WebSocketWatchDog(options);
        }
        WebSocketConnection connection = new WebSocketConnection(request, watchDog, autoClose);
        if (autoClose == false) {
            connections.put(Tuples.of(participantId, symbolName), connection);
        }
        connection.connect();
    }

    private <T> void createConnection(Integer participantId, String symbolName, WebsocketRequest<T> request) {
        createConnection(participantId, symbolName, request, false);
    }

    @Override
    public void unsubscribe(Integer participantId, String symbolName) {
        WebSocketConnection connection = connections.remove(Tuples.of(participantId, symbolName));
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
            Integer participantId,
            String symbolName,
            SubscriptionListener<AggregateTradeEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(participantId, symbolName, requestImpl.subscribeAggregateTradeEvent(symbolName, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeMarkPriceEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<MarkPriceEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(participantId, symbolName, requestImpl.subscribeMarkPriceEvent(symbolName, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeCandlestickEvent(
            Integer participantId,
            String symbolName,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeCandlestickEvent(symbolName, interval, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolMiniTickerEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<SymbolMiniTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeSymbolMiniTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllMiniTickerEvent(
            Integer participantId,
            SubscriptionListener<List<SymbolMiniTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                null,
                requestImpl.subscribeAllMiniTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolTickerEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<SymbolTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeSymbolTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllTickerEvent(
            Integer participantId,
            SubscriptionListener<List<SymbolTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                null,
                requestImpl.subscribeAllTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolBookTickerEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeSymbolBookTickerEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllBookTickerEvent(
            Integer participantId,
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                null,
                requestImpl.subscribeAllBookTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolLiquidationOrderEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeSymbolLiquidationOrderEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeAllLiquidationOrderEvent(
            Integer participantId,
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                null,
                requestImpl.subscribeAllLiquidationOrderEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeBookDepthEvent(
            Integer participantId,
            String symbolName,
            Integer limit,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeBookDepthEvent(symbolName, limit, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeDiffDepthEvent(
            Integer participantId,
            String symbolName,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                symbolName,
                requestImpl.subscribeDiffDepthEvent(symbolName, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeUserDataEvent(
            Integer participantId,
            String listenKey,
            SubscriptionListener<UserDataUpdateEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                null,
                requestImpl.subscribeUserDataEvent(listenKey, subscriptionListener, errorHandler)
        );
    }
}
