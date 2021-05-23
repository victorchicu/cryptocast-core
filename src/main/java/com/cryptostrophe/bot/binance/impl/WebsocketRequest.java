package com.cryptostrophe.bot.binance.impl;

import com.cryptostrophe.bot.binance.SubscriptionErrorHandler;
import com.cryptostrophe.bot.binance.SubscriptionListener;
import com.cryptostrophe.bot.binance.impl.utils.Handler;

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
