package com.coinbank.core.services;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.User;

import java.util.List;

public interface AssetService {
    void addAssetTickerEvent(User user, String assetName);

    void removeAssetTickerEvent(User user, String assetName);

    List<Asset> listAssets(User user);
}
