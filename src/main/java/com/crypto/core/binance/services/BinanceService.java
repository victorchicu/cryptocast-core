package com.crypto.core.binance.services;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;

import java.util.List;

public interface BinanceService {
    void subscribeOnTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback);

    void unsubscribeFromTickerEvent(String assetName);

    List<Asset> listAssets();
}
