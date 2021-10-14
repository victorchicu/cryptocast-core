package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;

    public BinanceServiceImpl(BinanceProperties binanceProperties, BinanceApiRestClient binanceApiRestClient) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
    }

    @Override
    public List<Asset> listAssets() {
        return binanceApiRestClient.listAssets(30000L, Instant.now().toEpochMilli());
    }
}
