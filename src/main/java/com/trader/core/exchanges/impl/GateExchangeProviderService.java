package com.trader.core.exchanges.impl;

import com.trader.core.binance.client.domain.account.AssetBalance;
import com.trader.core.exchanges.ExchangeProviderService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service("GATE")
public class GateExchangeProviderService implements ExchangeProviderService {
    @Override
    public void createAssetTicker(Principal principal, String assetName) {

    }

    @Override
    public void removeAssetTicker(String assetName) {

    }

    @Override
    public List<AssetBalance> listAssetBalances(Principal principal) {
        return null;
    }

    @Override
    public Optional<AssetBalance> findAssetBalanceByName(Principal principal, String assetName) {
        return Optional.empty();
    }
}
