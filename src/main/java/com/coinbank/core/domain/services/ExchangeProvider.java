package com.coinbank.core.domain.services;

import com.coinbank.core.domain.ApiKey;

public interface ExchangeProvider {
    ExchangeService get(ApiKey apiKey);
}
