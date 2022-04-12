package com.trader.core.services.impl;

import com.trader.core.domain.Asset;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.Ohlc;
import com.trader.core.domain.User;
import com.trader.core.services.ApiRestClient;
import com.trader.core.services.ApiWebSocketClient;
import com.trader.core.services.ExchangeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("GATE")
public class GateExchangeService implements ExchangeService {
    @Override
    public void createAssetTicker(User user, String assetName) {
        throw new UnsupportedOperationException("createAssetTicker");
    }

    @Override
    public void removeAssetTicker(String assetName) {
        throw new UnsupportedOperationException("removeAssetTicker");
    }

    @Override
    public List<Ohlc> listOhlc(String assetName, String interval, Long startTime, Long endTime) {
        throw new UnsupportedOperationException("listOhlc");
    }

    @Override
    public Set<String> availableAssets() {
        throw new UnsupportedOperationException("availableAssets");
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        throw new UnsupportedOperationException("newApiRestClient");
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        throw new UnsupportedOperationException("newApiWebSocketClient");
    }

    @Override
    public List<Asset> listAssetsBalances(User user) {
        throw new UnsupportedOperationException("listAssetsBalances");
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }

    @Override
    public Optional<Asset> findAssetByName(User user, String assetName) {
        throw new UnsupportedOperationException("findAssetByName");
    }
}
