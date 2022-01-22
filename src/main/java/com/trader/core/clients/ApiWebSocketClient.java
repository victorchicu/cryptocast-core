package com.trader.core.clients;

import com.trader.core.binance.domain.event.TickerEvent;

import java.io.Closeable;
import java.util.function.Consumer;

public interface ApiWebSocketClient {
    Closeable onTickerEvent(String symbol, Consumer<TickerEvent> consumer);
}
