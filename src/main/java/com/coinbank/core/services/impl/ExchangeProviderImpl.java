package com.coinbank.core.services.impl;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.services.ExchangeFactory;
import com.coinbank.core.services.ExchangeProvider;
import com.coinbank.core.services.ExchangeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeProviderImpl implements ExchangeProvider {
    private final Map<String, ExchangeFactory> exchangeFactory;

    public ExchangeProviderImpl(Map<String, ExchangeFactory> exchangeFactory) {
        this.exchangeFactory = exchangeFactory;
    }

    @Override
    public ExchangeService get(ApiKey apiKey) {
        return Optional.ofNullable(exchangeFactory.get(apiKey.getExchange().name()))
                .map(factory -> factory.create(apiKey.getApiKey(), apiKey.getSecretKey()))
                .orElseThrow(() -> new RuntimeException("Unsupported exchange factory: " + apiKey.getExchange()));
    }
}
