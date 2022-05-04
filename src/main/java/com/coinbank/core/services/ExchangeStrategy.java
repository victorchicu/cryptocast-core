package com.coinbank.core.services;

import com.coinbank.core.enums.ExchangeProvider;

public interface ExchangeStrategy {
    ExchangeService getExchangeService(ExchangeProvider exchangeProvider);
}
