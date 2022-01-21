package com.trader.core.services;

import com.trader.core.binance.domain.account.AssetBalance;

import java.security.Principal;
import java.util.List;

public interface AssetService {
    void addAssetTickerEvent(Principal principal, String assetName);

    void removeAssetTickerEvent(String assetName);

    List<AssetBalance> listAssetBalances(Principal principal);
}
