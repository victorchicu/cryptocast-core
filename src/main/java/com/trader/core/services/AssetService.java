package com.trader.core.services;

import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.domain.User;

import java.util.List;

public interface AssetService {
    void addAssetTickerEvent(User user, String assetName);

    void removeAssetTickerEvent(User user, String assetName);

    List<AssetBalance> listAssetBalances(User user);
}
