package com.crypto.core.auth.converters;

import com.crypto.core.auth.domain.Account;
import com.crypto.core.auth.dto.AuthRequestDto;
import com.crypto.core.oauth2.enums.AuthProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestDtoToAccountConverter implements Converter<AuthRequestDto, Account> {
    @Override
    public Account convert(AuthRequestDto source) {
        return Account.newBuilder()
                .email(source.getEmail())
                .password(source.getPassword())
                .build();
    }
}
