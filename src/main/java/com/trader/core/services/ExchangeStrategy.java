package com.trader.core.services;

import com.trader.core.enums.ExchangeProvider;

public interface ExchangeStrategy {
    ExchangeService getExchangeService(ExchangeProvider exchangeProvider);
}
