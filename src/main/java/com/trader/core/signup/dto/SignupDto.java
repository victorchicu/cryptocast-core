package com.trader.core.signup.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.enums.ExchangeProvider;

public class SignupDto {
    private final String email;
    private final String password;
    private final String apiKey;
    private final String secretKey;
    private final ExchangeProvider exchange;

    @JsonCreator
    public SignupDto(
            String email,
            String password,
            String apiKey,
            String secretKey,
            ExchangeProvider exchange
    ) {
        this.email = email;
        this.password = password;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.exchange = exchange;
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

    public ExchangeProvider getExchange() {
        return exchange;
    }
}
