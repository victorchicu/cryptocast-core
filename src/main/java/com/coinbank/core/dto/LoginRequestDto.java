package com.coinbank.core.dto;

import com.coinbank.core.enums.ExchangeProvider;
import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginRequestDto {
    private final String email;
    private final String password;
    private final ExchangeProvider exchangeProvider;

    @JsonCreator
    public LoginRequestDto(String email, String password, ExchangeProvider exchangeProvider) {
        this.email = email;
        this.password = password;
        this.exchangeProvider = exchangeProvider;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ExchangeProvider getExchangeProvider() {
        return exchangeProvider;
    }
}
