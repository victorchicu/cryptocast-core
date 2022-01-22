package com.trader.core.services;

import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface ExchangeProviderService {
    void createAssetTicker(User user, String assetName);

    void removeAssetTicker(String assetName);

    List<AssetBalance> listAssetBalances(User user);

    Optional<AssetBalance> findAssetBalanceByName(User user, String assetName);
}
