package com.trader.core.users.repository.converters;

import com.trader.core.users.domain.User;
import com.trader.core.users.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(source.getEmail());
        userEntity.setPassword(source.getPassword());
        userEntity.setImageUrl(source.getImageUrl());
        userEntity.setProvider(source.getProvider());
        userEntity.setProviderId(source.getProviderId());
        userEntity.setApiKey(source.getApiKey());
        userEntity.setSecretKey(source.getSecretKey());
        userEntity.setExchange(source.getExchange());
        return userEntity;
    }
}
