package com.trader.core.exchanges.strategy.impl;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.exchanges.ExchangeProviderService;
import com.trader.core.exchanges.exceptions.UnsupportedExchangeProviderService;
import com.trader.core.exchanges.strategy.ExchangeProviderServiceStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeProviderServiceStrategyImpl implements ExchangeProviderServiceStrategy {
    private final Map<ExchangeProvider, ExchangeProviderService> exchangeProviders;

    public ExchangeProviderServiceStrategyImpl(Map<ExchangeProvider, ExchangeProviderService> exchangeProviders) {
        this.exchangeProviders = exchangeProviders;
    }

    @Override
    public ExchangeProviderService getExchangeProvider(ExchangeProvider exchangeProvider) {
        return Optional.ofNullable(exchangeProviders.get(exchangeProvider))
                .orElseThrow(UnsupportedExchangeProviderService::new);
    }
}
