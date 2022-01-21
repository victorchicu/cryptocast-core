package com.trader.core.login.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.enums.ExchangeProvider;

public class LoginRequestDto {
    private final String email;
    private final String password;
    private final ExchangeProvider exchange;

    @JsonCreator
    public LoginRequestDto(String email, String password, ExchangeProvider exchange) {
        this.email = email;
        this.password = password;
        this.exchange = exchange;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ExchangeProvider getExchange() {
        return exchange;
    }
}
