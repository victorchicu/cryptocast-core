package com.crypto.core.auth.converters;

import com.crypto.core.users.domain.User;
import com.crypto.core.auth.dto.SignupRequestDto;
import com.crypto.core.auth.enums.AuthProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignupRequestDtoToUserConverter implements Converter<SignupRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    public SignupRequestDtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SignupRequestDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .provider(AuthProvider.LOCAL)
                .build();
    }
}
