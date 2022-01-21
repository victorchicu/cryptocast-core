package com.trader.core.converters;

import com.trader.core.enums.OAuth2Provider;
import com.trader.core.dto.SignupDto;
import com.trader.core.domain.User;
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
                .provider(OAuth2Provider.LOCAL)
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .exchange(source.getExchange())
                .build();
    }
}
