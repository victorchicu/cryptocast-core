package com.crypto.bot.binance.client.impl;

import com.crypto.bot.binance.client.SubscriptionClient;
import com.crypto.bot.binance.client.SubscriptionErrorHandler;
import com.crypto.bot.binance.client.SubscriptionListener;
import com.crypto.bot.binance.client.SubscriptionOptions;
import com.crypto.bot.binance.client.domain.user.UserDataUpdateEvent;
import com.crypto.bot.binance.client.domain.enums.CandlestickInterval;
import com.crypto.bot.binance.client.domain.event.AggregateTradeEvent;
import com.crypto.bot.binance.client.domain.event.CandlestickEvent;
import com.crypto.bot.binance.client.domain.event.LiquidationOrderEvent;
import com.crypto.bot.binance.client.domain.event.MarkPriceEvent;
import com.crypto.bot.binance.client.domain.event.OrderBookEvent;
import com.crypto.bot.binance.client.domain.event.SymbolBookTickerEvent;
import com.crypto.bot.binance.client.domain.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.client.domain.event.SymbolTickerEvent;

import java.util.*;

public class WebSocketStreamClientImpl implements SubscriptionClient {

    private final SubscriptionOptions options;
    private WebSocketWatchDog watchDog;

    private final WebsocketRequestImpl requestImpl;

    private final Map<Integer, WebSocketConnection> connections = new LinkedHashMap<>();

    WebSocketStreamClientImpl(SubscriptionOptions options) {
        this.watchDog = null;
        this.options = Objects.requireNonNull(options);

        this.requestImpl = new WebsocketRequestImpl();
    }

    private <T> void createConnection(Integer participantId, WebsocketRequest<T> request, boolean autoClose) {
        if (watchDog == null) {
            watchDog = new WebSocketWatchDog(options);
        }
        WebSocketConnection connection = new WebSocketConnection(request, watchDog, autoClose);
        if (autoClose == false) {
            connections.put(participantId, connection);
        }
        connection.connect();
    }

    private <T> void createConnection(Integer participantId, WebsocketRequest<T> request) {
        createConnection(participantId, request, false);
    }

    @Override
    public void unsubscribe(Integer participantId) {
        WebSocketConnection connection = connections.remove(participantId);
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
            String symbol,
            SubscriptionListener<AggregateTradeEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(participantId, requestImpl.subscribeAggregateTradeEvent(symbol, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeMarkPriceEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<MarkPriceEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(participantId, requestImpl.subscribeMarkPriceEvent(symbol, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeCandlestickEvent(
            Integer participantId,
            String symbol,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeCandlestickEvent(symbol, interval, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolMiniTickerEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<SymbolMiniTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeSymbolMiniTickerEvent(symbol, subscriptionListener, errorHandler)
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
                requestImpl.subscribeAllMiniTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolTickerEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<SymbolTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeSymbolTickerEvent(symbol, subscriptionListener, errorHandler)
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
                requestImpl.subscribeAllTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolBookTickerEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeSymbolBookTickerEvent(symbol, subscriptionListener, errorHandler)
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
                requestImpl.subscribeAllBookTickerEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeSymbolLiquidationOrderEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeSymbolLiquidationOrderEvent(symbol, subscriptionListener, errorHandler)
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
                requestImpl.subscribeAllLiquidationOrderEvent(subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeBookDepthEvent(
            Integer participantId,
            String symbol,
            Integer limit,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeBookDepthEvent(symbol, limit, subscriptionListener, errorHandler)
        );
    }

    @Override
    public void subscribeDiffDepthEvent(
            Integer participantId,
            String symbol,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler
    ) {
        createConnection(
                participantId,
                requestImpl.subscribeDiffDepthEvent(symbol, subscriptionListener, errorHandler)
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
                requestImpl.subscribeUserDataEvent(listenKey, subscriptionListener, errorHandler)
        );
    }
}
