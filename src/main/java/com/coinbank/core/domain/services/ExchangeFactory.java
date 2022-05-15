package com.coinbank.core.domain.services;

public interface ExchangeFactory {
    ExchangeService create(String apiKey, String secretKey);
}
