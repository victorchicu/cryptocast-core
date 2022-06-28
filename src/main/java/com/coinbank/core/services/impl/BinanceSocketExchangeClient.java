package com.coinbank.core.services.impl;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.TickerEvent;
import com.coinbank.core.configs.BinanceConfig;
import com.coinbank.core.services.SocketExchangeClient;

import java.io.Closeable;
import java.util.function.Consumer;

public class BinanceSocketExchangeClient implements SocketExchangeClient {
    private final BinanceConfig binanceConfig;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceSocketExchangeClient(BinanceConfig binanceConfig, BinanceApiWebSocketClient binanceApiWebSocketClient) {
        this.binanceConfig = binanceConfig;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public Closeable onTickerEvent(String assetName, Consumer<TickerEvent> consumer) {
        String symbolName = assetName; //getSymbolName(assetName);
        return binanceApiWebSocketClient.onTickerEvent(symbolName.toLowerCase(), consumer::accept);
    }
}
