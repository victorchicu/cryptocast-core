package com.coinbank.core.services;

import com.coinbank.core.domain.ApiKey;

public interface ExchangeProvider {
    ExchangeService get(ApiKey apiKey);
}
