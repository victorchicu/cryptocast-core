package com.trader.core.users.repository.converters;

import com.trader.core.users.domain.User;
import com.trader.core.users.entity.UserEntity;
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
                .provider(source.getProvider())
                .providerId(source.getProviderId())
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .build();
    }
}
