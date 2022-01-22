package com.trader.core.strategy.impl;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.services.ExchangeService;
import com.trader.core.exceptions.UnsupportedExchangeProviderService;
import com.trader.core.strategy.ExchangeStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeStrategyImpl implements ExchangeStrategy {
    private final Map<ExchangeProvider, ExchangeService> exchanges;

    public ExchangeStrategyImpl(Map<ExchangeProvider, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public ExchangeService getExchangeService(ExchangeProvider exchangeProvider) {
        return Optional.ofNullable(exchanges.get(exchangeProvider))
                .orElseThrow(UnsupportedExchangeProviderService::new);
    }
}
