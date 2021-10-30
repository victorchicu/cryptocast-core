package com.crypto.core.binance.assets.services;

import com.crypto.core.binance.client.domain.account.AssetBalance;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AssetService {
    void addTickerEvent(Principal principal, String assetName);

    void removeTickerEvent(String assetName);

    List<AssetBalance> listAssets(Principal principal);

    Optional<AssetBalance> findAssetByName(Principal principal, String assetName);
}
