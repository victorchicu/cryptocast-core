package com.coinbank.core.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.dto.SignupRequestDto;
import com.coinbank.core.enums.OAuth2Provider;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter implements Converter<SignupRequestDto, User> {
    private final PasswordEncoder passwordEncoder;

    public DtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SignupRequestDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .auth2Provider(OAuth2Provider.LOCAL)
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .exchangeProvider(source.getExchangeProvider())
                .build();
    }
}
