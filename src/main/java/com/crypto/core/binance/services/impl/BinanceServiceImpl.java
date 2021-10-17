package com.crypto.core.binance.services.impl;

import com.crypto.core.binance.client.BinanceApiCallback;
import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.watchlist.services.WatchlistService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BinanceServiceImpl implements BinanceService {
    private static final long DEFAULT_RECV_WINDOW = 30000L;

    private final WatchlistService watchlistService;
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceServiceImpl(
            WatchlistService watchlistService,
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.watchlistService = watchlistService;
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void registerTickerEvent(String assetName, BinanceApiCallback<TickerEvent> callback) {
        String symbolName = getSymbol(assetName);
        binanceApiWebSocketClient.onTickerEvent(symbolName, callback);
    }

    @Override
    public void removeTickerEvent(String assetName) {
        //TODO:
    }

    @Override
    public List<Asset> listAssets(Principal principal) {
        return binanceApiRestClient.listAssets(DEFAULT_RECV_WINDOW, Instant.now().toEpochMilli()).stream()
                .filter(asset -> !binanceProperties.getBlacklist().contains(asset.getCoin()) && asset.getFree().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Asset::getFree).reversed())
                .collect(Collectors.toList());
    }

    private String getSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.Asset::getCoin)
                .map(String::toLowerCase)
                .orElse(assetName);
    }
}
