package com.crypto.core.signup.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SignupRequestDto {
    private final String email;
    private final String password;

    @JsonCreator
    public SignupRequestDto(String email, String password) {
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
