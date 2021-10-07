package com.crypto.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AccessTokenResponseDto {
    private final String accessToken;

    @JsonCreator
    public AccessTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
