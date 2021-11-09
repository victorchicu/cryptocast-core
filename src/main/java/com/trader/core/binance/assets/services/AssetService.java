package com.trader.core.binance.assets.services;

import com.trader.core.binance.client.domain.account.AssetBalance;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AssetService {
    void addAssetTickerEvent(Principal principal, String assetName);

    void removeAssetTickerEvent(String assetName);

    List<AssetBalance> listAssets(Principal principal);

    Optional<AssetBalance> findAssetByName(Principal principal, String assetName);
}
