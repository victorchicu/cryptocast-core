package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceServiceImpl(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void subscribeOnTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback) {
        binanceApiWebSocketClient.onTickerEvent(assetName, callback);
    }

    @Override
    public void unsubscribeFromTickerEvent(String assetName) {
        //TODO: Unsubscribe from ticker event
        binanceApiWebSocketClient.close();
    }

    @Override
    public List<Asset> listAssets() {
        return binanceApiRestClient.listAssets(30000L, Instant.now().toEpochMilli());
    }
}
