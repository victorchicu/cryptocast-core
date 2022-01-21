package com.trader.core.services;

import com.trader.core.binance.domain.account.AssetBalance;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ExchangeProviderService {
    void createAssetTicker(Principal principal, String assetName);

    void removeAssetTicker(String assetName);

    List<AssetBalance> listAssetBalances(Principal principal);

    Optional<AssetBalance> findAssetBalanceByName(Principal principal, String assetName);
}
