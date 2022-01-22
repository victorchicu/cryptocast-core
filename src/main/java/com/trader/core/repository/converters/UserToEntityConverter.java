package com.trader.core.repository.converters;

import com.trader.core.domain.User;
import com.trader.core.entity.UserEntity;
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
        userEntity.setProvider(source.getAuth2Provider());
        userEntity.setProviderId(source.getProviderId());
        userEntity.setApiKey(source.getApiKey());
        userEntity.setSecretKey(source.getSecretKey());
        userEntity.setExchangeProvider(source.getExchangeProvider());
        return userEntity;
    }
}
