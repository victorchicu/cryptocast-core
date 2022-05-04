package com.coinbank.core.services.impl;

import com.coinbank.core.services.ExchangeStrategy;
import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.User;
import com.coinbank.core.services.AssetService;
import com.coinbank.core.services.ExchangeService;
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
