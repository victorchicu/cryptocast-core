package com.trader.core.services.impl;

import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.domain.User;
import com.trader.core.services.ExchangeProviderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("GATE")
public class GateExchangeProviderService implements ExchangeProviderService {
    @Override
    public void createAssetTicker(User user, String assetName) {

    }

    @Override
    public void removeAssetTicker(String assetName) {

    }

    @Override
    public List<AssetBalance> listAssetBalances(User user) {
        return null;
    }

    @Override
    public Optional<AssetBalance> findAssetBalanceByName(User user, String assetName) {
        return Optional.empty();
    }
}
