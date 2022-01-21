package com.trader.core.assets.services.impl;

import com.trader.core.assets.services.AssetService;
import com.trader.core.binance.client.domain.account.AssetBalance;
import com.trader.core.enums.ExchangeProvider;
import com.trader.core.exchanges.ExchangeProviderService;
import com.trader.core.exchanges.strategy.ExchangeProviderServiceStrategy;
import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final SubscriptionService subscriptionService;
    private final ExchangeProviderServiceStrategy exchangeProviderServiceStrategy;

    public AssetServiceImpl(
            SubscriptionService subscriptionService,
            ExchangeProviderServiceStrategy exchangeProviderServiceStrategy
    ) {
        this.subscriptionService = subscriptionService;
        this.exchangeProviderServiceStrategy = exchangeProviderServiceStrategy;
    }

    @Override
    public void addAssetTickerEvent(Principal principal, String assetName) {
        ExchangeProviderService provider = exchangeProviderServiceStrategy.getExchangeProvider(
                ExchangeProvider.BINANCE
        );
        provider.createAssetTicker(principal, assetName);
    }


    @Override
    public void removeAssetTickerEvent(String assetName) {
        ExchangeProviderService provider = exchangeProviderServiceStrategy.getExchangeProvider(
                ExchangeProvider.BINANCE
        );
        provider.removeAssetTicker(assetName);
    }

    @Override
    public List<AssetBalance> listAssetBalances(Principal principal) {
        ExchangeProviderService provider = exchangeProviderServiceStrategy.getExchangeProvider(
                ExchangeProvider.BINANCE
        );
        Page<Subscription> page = subscriptionService.findSubscriptions(
                principal,
                Pageable.unpaged()
        );
        List<AssetBalance> assetBalances = provider.listAssetBalances(principal);
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
                    addAssetTickerEvent(principal, assetBalance.getAsset());
                }
            });
        }
        return assetBalances;
    }
}
