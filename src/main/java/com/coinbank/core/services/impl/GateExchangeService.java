package com.coinbank.core.services.impl;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.AssetPrice;
import com.coinbank.core.domain.Ohlc;
import com.coinbank.core.domain.User;
import com.coinbank.core.services.ApiRestClient;
import com.coinbank.core.services.ApiWebSocketClient;
import com.coinbank.core.services.ExchangeService;
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
    public List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end) {
        throw new UnsupportedOperationException("listOhlc");
    }

    @Override
    public List<Asset> listAssets(User user, Set<String> assets) {
        throw new UnsupportedOperationException("listAssets");
    }

    @Override
    public Set<String> availableAssets() {
        throw new UnsupportedOperationException("availableAssets");
    }

    @Override
    public Optional<Asset> findAssetByName(User user, String assetName) {
        throw new UnsupportedOperationException("findAssetByName");
    }

    @Override
    public Optional<AssetPrice> getAssetPrice(User user, String assetName) {
        throw new UnsupportedOperationException("getAssetPrice");
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        throw new UnsupportedOperationException("newApiRestClient");
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        throw new UnsupportedOperationException("newApiWebSocketClient");
    }
}
