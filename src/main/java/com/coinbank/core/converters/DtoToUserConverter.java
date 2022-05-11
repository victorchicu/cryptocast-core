package com.coinbank.core.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.dto.SignupDto;
import com.coinbank.core.enums.OAuth2Provider;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter implements Converter<SignupDto, User> {
    private final PasswordEncoder passwordEncoder;

    public DtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SignupDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .auth2Provider(OAuth2Provider.LOCAL)
                //TODO: Refactor providers and API credentials
                //.apiKey(source.getApiKey())
                //.secretKey(source.getSecretKey())
                //.exchangeProvider(source.getExchangeProvider())
                .build();
    }
}
