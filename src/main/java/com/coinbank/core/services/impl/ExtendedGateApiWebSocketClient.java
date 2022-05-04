package com.coinbank.core.services.impl;

import com.binance.api.client.domain.event.TickerEvent;
import com.coinbank.core.services.ApiWebSocketClient;

import java.io.Closeable;
import java.util.function.Consumer;

public class ExtendedGateApiWebSocketClient implements ApiWebSocketClient {
    @Override
    public Closeable onTickerEvent(String assetName, Consumer<TickerEvent> consumer) {
        throw new UnsupportedOperationException("Gate not support on ticker event");
    }
}
