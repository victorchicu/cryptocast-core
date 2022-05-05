package com.coinbank.core.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.dto.SigninDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestDtoToUserConverter implements Converter<SigninDto, User> {
    private final PasswordEncoder passwordEncoder;

    public LoginRequestDtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SigninDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .build();
    }
}
