package com.crypto.core.wallet.services.impl;

import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.wallet.services.WalletService;
import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.services.WatchlistService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: listAssets is used in a not optimized way due to no alternative API at the moment on Binance platform.

@Service
public class WalletServiceImpl implements WalletService {
    private final BinanceService binanceService;
    private final WatchlistService watchlistService;

    public WalletServiceImpl(BinanceService binanceService, WatchlistService watchlistService) {
        this.binanceService = binanceService;
        this.watchlistService = watchlistService;
    }

    @Override
    public List<Asset> listAssets(Principal principal) {
        Page<Subscription> page = watchlistService.findSubscriptions(principal, Pageable.unpaged());
        Set<String> assetNames = page.getContent().stream().map(Subscription::getAssetName).collect(Collectors.toSet());
        List<Asset> assets = ListUtils.emptyIfNull(binanceService.listAssets(principal));
        assets.forEach(asset -> asset.setFlagged(assetNames.contains(asset.getCoin())));
        return assets;
    }

    @Override
    public Optional<Asset> findAssetByName(Principal principal, String assetName) {
        return listAssets(principal).stream()
                .filter(asset -> asset.getCoin().equals(assetName))
                .findFirst();
    }
}
