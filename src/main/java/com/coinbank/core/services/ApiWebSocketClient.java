package com.coinbank.core.services;

import com.binance.api.client.domain.event.TickerEvent;

import java.io.Closeable;
import java.util.function.Consumer;

public interface ApiWebSocketClient {
    Closeable onTickerEvent(String assetName, Consumer<TickerEvent> consumer);
}
