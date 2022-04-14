package com.trader.core.services.impl;

import com.trader.core.domain.Asset;
import com.trader.core.domain.User;
import com.trader.core.services.AssetService;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public List<Asset> listAssets(User user, Set<String> assets) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        return exchangeService.listAssets(user, assets);
    }
}
