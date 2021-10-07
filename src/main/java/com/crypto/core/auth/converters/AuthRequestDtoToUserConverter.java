package com.crypto.core.auth.converters;

import com.crypto.core.users.domain.User;
import com.crypto.core.auth.dto.AuthRequestDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestDtoToUserConverter implements Converter<AuthRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    public AuthRequestDtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(AuthRequestDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .build();
    }
}
