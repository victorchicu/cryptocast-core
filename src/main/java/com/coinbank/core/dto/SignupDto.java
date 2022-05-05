package com.coinbank.core.dto;

import com.coinbank.core.enums.ExchangeProvider;
import com.fasterxml.jackson.annotation.JsonCreator;

public class SignupDto {
    private final String email;
    private final String password;
    private final String apiKey;
    private final String secretKey;
    private final ExchangeProvider exchangeProvider;

    @JsonCreator
    public SignupDto(
            String email,
            String password,
            String apiKey,
            String secretKey,
            ExchangeProvider exchangeProvider
    ) {
        this.email = email;
        this.password = password;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.exchangeProvider = exchangeProvider;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
