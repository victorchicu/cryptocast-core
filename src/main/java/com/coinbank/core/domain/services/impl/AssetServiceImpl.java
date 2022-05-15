package com.coinbank.core.domain.services.impl;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.User;
import com.coinbank.core.domain.services.AssetService;
import com.coinbank.core.domain.services.ExchangeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {
    private final Map<String, ExchangeService> exchanges;

    public AssetServiceImpl(Map<String, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public void addAssetTickerEvent(User user, String assetName) {
//        ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//        exchangeService.addAssetTicker(user, assetName);
    }


    @Override
    public void removeAssetTickerEvent(User user, String assetName) {
//        ExchangeService exchangeService = exchangeFactory.create(, ExchangeProvider.BINANCE);
//        exchangeService.removeAssetTicker(assetName);
    }

    @Override
    public List<Asset> listAssets(User user) {
        return user.getApiKeys().entrySet().stream()
                .map(entry ->
                        Optional.ofNullable(exchanges.get(entry.getKey()))
                                .map(exchangeService -> exchangeService.listAssets(entry.getKey(), user))
                                .orElseThrow(() -> new RuntimeException("Unsupported exchange service: " + entry.getKey()))
                )
                .collect(ArrayList::new, List::addAll, List::addAll);
    }
}
