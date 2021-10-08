package com.crypto.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginRequestDto {
    private final String email;
    private final String password;

    @JsonCreator
    public LoginRequestDto(String email, String password) {
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
