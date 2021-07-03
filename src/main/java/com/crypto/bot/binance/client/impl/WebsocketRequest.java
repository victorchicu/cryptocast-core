package com.crypto.bot.binance.client.impl;

import com.crypto.bot.binance.client.SubscriptionErrorHandler;
import com.crypto.bot.binance.client.SubscriptionListener;
import com.crypto.bot.binance.client.impl.utils.Handler;

class WebsocketRequest<T> {

    WebsocketRequest(SubscriptionListener<T> listener, SubscriptionErrorHandler errorHandler) {
        this.updateCallback = listener;
        this.errorHandler = errorHandler;
    }

    String signatureVersion = "2";
    String name;
    Handler<WebSocketConnection> connectionHandler;
    Handler<WebSocketConnection> authHandler = null;
    final SubscriptionListener<T> updateCallback;
    RestApiJsonParser<T> jsonParser;
    final SubscriptionErrorHandler errorHandler;
}
