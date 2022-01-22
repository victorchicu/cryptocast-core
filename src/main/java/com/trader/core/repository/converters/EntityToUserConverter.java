package com.trader.core.repository.converters;

import com.trader.core.domain.User;
import com.trader.core.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToUserConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity source) {
        return User.newBuilder()
                .id(source.getId())
                .email(source.getEmail())
                .password(source.getPassword())
                .imageUrl(source.getImageUrl())
                .auth2Provider(source.getProvider())
                .providerId(source.getProviderId())
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .exchangeProvider(source.getExchangeProvider())
                .build();
    }
}
