package com.coinbank.core.repository.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.entity.UserEntity;
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
        userEntity.setAuth2Provider(source.getAuth2Provider());
        userEntity.setProviderId(source.getProviderId());
        userEntity.setApiKey(source.getApiKey());
        userEntity.setSecretKey(source.getSecretKey());
        userEntity.setExchangeProvider(source.getExchangeProvider());
        return userEntity;
    }
}
