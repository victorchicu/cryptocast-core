package com.trader.core.clients.impl;

import com.trader.core.binance.BinanceApiWebSocketClient;
import com.trader.core.binance.domain.event.TickerEvent;
import com.trader.core.clients.ApiWebSocketClient;

import java.io.Closeable;
import java.util.function.Consumer;

public class ExtendedBinanceApiWebSocketClient implements ApiWebSocketClient {
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public ExtendedBinanceApiWebSocketClient(BinanceApiWebSocketClient binanceApiWebSocketClient) {
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public Closeable onTickerEvent(String symbol, Consumer<TickerEvent> consumer) {
        return binanceApiWebSocketClient.onTickerEvent(
                symbol,
                response -> consumer.accept(response)
        );
    }
}
