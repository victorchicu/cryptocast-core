package com.trader.core.strategy;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.services.ExchangeService;

public interface ExchangeStrategy {
    ExchangeService getExchangeService(ExchangeProvider exchangeProvider);
}
