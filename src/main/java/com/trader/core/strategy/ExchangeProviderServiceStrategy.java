package com.trader.core.strategy;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.services.ExchangeProviderService;

public interface ExchangeProviderServiceStrategy {
    ExchangeProviderService getExchangeProvider(ExchangeProvider exchangeProvider);
}
