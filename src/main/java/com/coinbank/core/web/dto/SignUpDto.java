package com.coinbank.core.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SignUpDto {
    private final String email;
    private final String password;

    @JsonCreator
    public SignUpDto(String email, String password) {
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
