package com.trader.core.services;

import com.trader.core.domain.Asset;
import com.trader.core.domain.AssetPrice;
import com.trader.core.domain.Ohlc;
import com.trader.core.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExchangeService {
    void createAssetTicker(User user, String assetName);

    void removeAssetTicker(String assetName);

    List<Ohlc> listOhlc(String assetName, String interval, Long start, Long end);

    List<Asset> listAssets(User user, Set<String> assets);

    Set<String> availableAssets();

    ApiRestClient newApiRestClient(User user);

    ApiWebSocketClient newApiWebSocketClient(User user);

    Optional<Asset> findAssetByName(User user, String assetName);

    Optional<AssetPrice> getAssetPrice(User user, String assetName);
}
