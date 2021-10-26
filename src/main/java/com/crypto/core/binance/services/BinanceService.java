package com.crypto.core.binance.services;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.domain.account.AssetBalance;
import com.crypto.core.binance.client.domain.event.TickerEvent;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BinanceService {
    void addTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback);

    void removeTickerEvent(String assetName);

    List<AssetBalance> listAssets(Principal principal);

    Optional<AssetBalance> findAssetByName(Principal principal, String assetName);
}
