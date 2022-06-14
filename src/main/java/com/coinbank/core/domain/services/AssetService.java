package com.coinbank.core.domain.services;

import com.coinbank.core.domain.Asset;
import com.coinbank.core.domain.User;

import java.util.List;

public interface AssetService {
    List<Asset> listAssets(User user, String label);
}
