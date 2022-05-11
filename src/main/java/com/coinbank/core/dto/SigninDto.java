package com.coinbank.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SigninDto {
    private final String email;
    private final String password;

    @JsonCreator
    public SigninDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
