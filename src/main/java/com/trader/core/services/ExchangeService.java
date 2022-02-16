package com.trader.core.services;

import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void createAssetTicker(User user, String assetName);

    void removeAssetTicker(String assetName);

    Set<String> availableAssets();

    ApiRestClient newApiRestClient(User user);

    ApiWebSocketClient newApiWebSocketClient(User user);

    List<AssetBalance> listAssetsBalances(User user);

    Optional<AssetPrice> getAssetPrice(User user, String assetName);

    Optional<AssetBalance> findAssetByName(User user, String assetName);
}
