package com.trader.core.services.impl;

import com.trader.core.domain.User;
import com.trader.core.services.AssetService;
import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.enums.ExchangeProvider;
import com.trader.core.services.ExchangeService;
import com.trader.core.strategy.ExchangeStrategy;
import com.trader.core.domain.Subscription;
import com.trader.core.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final SubscriptionService subscriptionService;
    private final ExchangeStrategy exchangeStrategy;

    public AssetServiceImpl(
            SubscriptionService subscriptionService,
            ExchangeStrategy exchangeStrategy
    ) {
        this.subscriptionService = subscriptionService;
        this.exchangeStrategy = exchangeStrategy;
    }

    @Override
    public void addAssetTickerEvent(User user, String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                ExchangeProvider.BINANCE
        );
        exchangeService.createAssetTicker(user, assetName);
    }


    @Override
    public void removeAssetTickerEvent(String assetName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                ExchangeProvider.BINANCE
        );
        exchangeService.removeAssetTicker(assetName);
    }

    @Override
    public List<AssetBalance> listAssetBalances(User user) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                user.getExchangeProvider()
        );
        Page<Subscription> page = subscriptionService.findSubscriptions(
                user,
                Pageable.unpaged()
        );
        List<AssetBalance> assetBalances = exchangeService.listAssetBalances(user);
        List<Subscription> subscriptions = page.getContent();
        if (!subscriptions.isEmpty()) {
            assetBalances.forEach(assetBalance -> {
                assetBalance.setFlagged(
                        subscriptions.stream()
                                .anyMatch(subscription ->
                                        subscription.getAssetName().equals(assetBalance.getAsset())
                                )
                );
                if (assetBalance.getFlagged()) {
                    removeAssetTickerEvent(assetBalance.getAsset());
                    addAssetTickerEvent(user, assetBalance.getAsset());
                }
            });
        }
        return assetBalances;
    }
}
