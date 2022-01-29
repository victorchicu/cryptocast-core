package com.trader.core.services;

import com.trader.core.domain.AssetChip;
import com.trader.core.domain.User;

import java.util.List;

public interface AssetChipService {
    void removeAssetChip(String assetName, User user);

    AssetChip addAssetChip(AssetChip assetChip);

    List<AssetChip> listAssetChips(User user);
}
