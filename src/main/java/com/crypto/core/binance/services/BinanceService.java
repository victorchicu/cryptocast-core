package com.crypto.core.binance.services;

import com.crypto.core.binance.client.domain.wallet.Asset;

import java.util.List;

public interface BinanceService {
    List<Asset> listAssets();
}
