package com.trader.core.clients.impl;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.TickerEvent;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.exceptions.AssetNotFoundException;

import java.io.Closeable;
import java.util.Optional;
import java.util.function.Consumer;

public class ExtendedBinanceApiWebSocketClient implements ApiWebSocketClient {
    private final BinanceProperties binanceProperties;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public ExtendedBinanceApiWebSocketClient(
            BinanceProperties binanceProperties,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public Closeable onTickerEvent(String assetName, Consumer<TickerEvent> consumer) {
        String symbolName = getSymbolName(assetName);
        return binanceApiWebSocketClient.onTickerEvent(symbolName.toLowerCase(), consumer::accept);
    }


    private String getSymbolName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }
}
