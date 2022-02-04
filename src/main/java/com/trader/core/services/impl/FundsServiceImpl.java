package com.trader.core.services.impl;

import com.trader.core.domain.FundsBalance;
import com.trader.core.domain.Subscription;
import com.trader.core.domain.User;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.FundsService;
import com.trader.core.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundsServiceImpl implements FundsService {
    private final ExchangeStrategy exchangeStrategy;
    private final SubscriptionService subscriptionService;

    public FundsServiceImpl(
            ExchangeStrategy exchangeStrategy,
            SubscriptionService subscriptionService
    ) {
        this.exchangeStrategy = exchangeStrategy;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void addFundsTickerEvent(User user, String fundsName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.createFundsTicker(user, fundsName);
    }


    @Override
    public void removeFundsTickerEvent(User user, String fundsName) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        exchangeService.removeFundsTicker(fundsName);
    }

    @Override
    public List<FundsBalance> listFundsBalances(User user) {
        ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
        List<FundsBalance> fundsBalances = exchangeService.listFundsBalances(user);
        Page<Subscription> page = subscriptionService.findSubscriptions(
                user,
                Pageable.unpaged()
        );
        List<Subscription> subscriptions = page.getContent();
        if (!subscriptions.isEmpty()) {
            fundsBalances.forEach(fundsBalance -> {
                fundsBalance.setFlagged(
                        subscriptions.stream()
                                .anyMatch(subscription ->
                                        subscription.getFundsName().equals(fundsBalance.getAsset())
                                )
                );
                if (fundsBalance.getFlagged()) {
                    removeFundsTickerEvent(user, fundsBalance.getAsset());
                    addFundsTickerEvent(user, fundsBalance.getAsset());
                }
            });
        }
        return fundsBalances;
    }
}
