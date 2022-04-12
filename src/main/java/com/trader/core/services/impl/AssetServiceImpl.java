package com.trader.core.services.impl;

import com.trader.core.domain.Asset;
import com.trader.core.domain.User;
import com.trader.core.services.AssetService;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final ExchangeStrategy exchangeStrategy;

    public AssetServiceImpl(ExchangeStrategy exchangeStrategy) {
        this.exchangeStrategy = exchangeStrategy;
    }

    @Override
    public void addAssetTickerEvent(User user, String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.createAssetTicker(user, assetName);
    }


    @Override
    public void removeAssetTickerEvent(User user, String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.removeAssetTicker(assetName);
    }

    @Override
    public List<Asset> listAssetsBalances(User user) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        return exchangeService.listAssetsBalances(user);
    }
}
