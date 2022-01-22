package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.enums.ExchangeProvider;

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
