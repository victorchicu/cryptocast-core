package com.trader.core.services.impl;

import com.trader.core.domain.AssetBalance;
import com.trader.core.domain.Subscription;
import com.trader.core.domain.User;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.AssetService;
import com.trader.core.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final ExchangeStrategy exchangeStrategy;
    private final SubscriptionService subscriptionService;

    public AssetServiceImpl(
            ExchangeStrategy exchangeStrategy,
            SubscriptionService subscriptionService
    ) {
        this.exchangeStrategy = exchangeStrategy;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void addAssetTickerEvent(User user, String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.createAssetTicker(user, assetName);
    }


    @Override
    public void removeAssetTickerEvent(User user, String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.removeAssetTicker(assetName);
    }

    @Override
    public List<AssetBalance> listAssetsBalances(User user) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        Page<Subscription> subscriptions = subscriptionService.findSubscriptions(user, Pageable.unpaged());
        List<AssetBalance> assetsBalances = exchangeService.listAssetsBalances(user);
        if (!subscriptions.isEmpty()) {
            assetsBalances.forEach(assetBalance -> {
                assetBalance.setFlagged(
                        subscriptions.stream()
                                .anyMatch(subscription ->
                                        subscription.getAssetName().equals(assetBalance.getAsset())
                                )
                );
                if (assetBalance.getFlagged()) {
                    removeAssetTickerEvent(user, assetBalance.getAsset());
                    addAssetTickerEvent(user, assetBalance.getAsset());
                }
            });
        }
        return assetsBalances;
    }
}
