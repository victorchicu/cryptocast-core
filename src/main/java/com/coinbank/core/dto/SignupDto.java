package com.coinbank.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SignupDto {
    private final String email;
    private final String password;

    @JsonCreator
    public SignupDto(String email, String password) {
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
