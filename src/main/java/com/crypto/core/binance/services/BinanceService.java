package com.crypto.core.binance.services;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;

import java.security.Principal;
import java.util.List;

public interface BinanceService {
    void registerTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback);

    void removeTickerEvent(String assetName);

    List<Asset> listAssets(Principal principal);
}
