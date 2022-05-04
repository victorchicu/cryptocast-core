package com.coinbank.core.services.impl;

import com.coinbank.core.services.ExchangeStrategy;
import com.coinbank.core.enums.ExchangeProvider;
import com.coinbank.core.services.ExchangeService;
import com.coinbank.core.exceptions.UnsupportedExchangeProviderService;
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
