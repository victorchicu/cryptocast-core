package com.trader.core.converters;

import com.trader.core.domain.User;
import com.trader.core.dto.LoginRequestDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestDtoToUserConverter implements Converter<LoginRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    public LoginRequestDtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(LoginRequestDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .build();
    }
}
