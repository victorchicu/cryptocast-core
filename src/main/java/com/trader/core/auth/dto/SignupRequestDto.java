package com.trader.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SignupRequestDto {
    private final String email;
    private final String password;
    private final String apiKey;
    private final String secretKey;

    @JsonCreator
    public SignupRequestDto(String email, String password, String apiKey, String secretKey) {
        this.email = email;
        this.password = password;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
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
}
