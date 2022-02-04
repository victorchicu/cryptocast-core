package com.trader.core.clients;

import com.binance.api.client.domain.event.TickerEvent;

import java.io.Closeable;
import java.util.function.Consumer;

public interface ApiWebSocketClient {
    Closeable onTickerEvent(String fundsName, Consumer<TickerEvent> consumer);
}
