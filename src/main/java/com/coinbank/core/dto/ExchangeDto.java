package com.coinbank.core.dto;

import com.coinbank.core.enums.ExchangeProvider;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ExchangeDto {
    private final String name;
    private final String apiKey;
    private final String secretKey;
    private final ExchangeProvider exchangeProvider;

    @JsonCreator
    public ExchangeDto(String name, String apiKey, String secretKey, ExchangeProvider exchangeProvider) {
        this.name = name;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.exchangeProvider = exchangeProvider;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public ExchangeProvider getExchangeProvider() {
        return exchangeProvider;
    }
}
