package com.coinbank.core.services;

public interface ExchangeFactory {
    ExchangeService create(String apiKey, String secretKey);
}
