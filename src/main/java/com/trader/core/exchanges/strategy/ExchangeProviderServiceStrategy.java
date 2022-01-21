package com.trader.core.exchanges.strategy;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.exchanges.ExchangeProviderService;

public interface ExchangeProviderServiceStrategy {
    ExchangeProviderService getExchangeProvider(ExchangeProvider exchangeProvider);
}
