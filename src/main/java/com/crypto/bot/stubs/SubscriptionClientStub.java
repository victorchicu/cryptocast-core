package com.crypto.bot.stubs;

import com.crypto.bot.binance.client.SubscriptionClient;
import com.crypto.bot.binance.client.SubscriptionErrorHandler;
import com.crypto.bot.binance.client.SubscriptionListener;
import com.crypto.bot.binance.client.domain.enums.CandlestickInterval;
import com.crypto.bot.binance.client.domain.event.*;
import com.crypto.bot.binance.client.domain.user.UserDataUpdateEvent;

import java.util.List;

public class SubscriptionClientStub implements SubscriptionClient {
    @Override
    public void unsubscribe(Integer participantId, String symbolName) {

    }

    @Override
    public void unsubscribeAll() {

    }

    @Override
    public void subscribeAggregateTradeEvent(Integer participantId, String symbol, SubscriptionListener<AggregateTradeEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeMarkPriceEvent(Integer participantId, String symbol, SubscriptionListener<MarkPriceEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeCandlestickEvent(Integer participantId, String symbol, CandlestickInterval interval, SubscriptionListener<CandlestickEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolMiniTickerEvent(Integer participantId, String symbol, SubscriptionListener<SymbolMiniTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllMiniTickerEvent(Integer participantId, SubscriptionListener<List<SymbolMiniTickerEvent>> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolTickerEvent(Integer participantId, String symbol, SubscriptionListener<SymbolTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllTickerEvent(Integer participantId, SubscriptionListener<List<SymbolTickerEvent>> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolBookTickerEvent(Integer participantId, String symbol, SubscriptionListener<SymbolBookTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllBookTickerEvent(Integer participantId, SubscriptionListener<SymbolBookTickerEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeSymbolLiquidationOrderEvent(Integer participantId, String symbol, SubscriptionListener<LiquidationOrderEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeAllLiquidationOrderEvent(Integer participantId, SubscriptionListener<LiquidationOrderEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeBookDepthEvent(Integer participantId, String symbol, Integer limit, SubscriptionListener<OrderBookEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeDiffDepthEvent(Integer participantId, String symbol, SubscriptionListener<OrderBookEvent> callback, SubscriptionErrorHandler errorHandler) {

    }

    @Override
    public void subscribeUserDataEvent(Integer participantId, String listenKey, SubscriptionListener<UserDataUpdateEvent> callback, SubscriptionErrorHandler errorHandler) {

    }
}
