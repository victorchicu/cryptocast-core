package com.trader.core.users.repository.converters;

import com.trader.core.users.domain.User;
import com.trader.core.users.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        UserEntity accountEntity = new UserEntity();
        accountEntity.setEmail(source.getEmail());
        accountEntity.setPassword(source.getPassword());
        accountEntity.setImageUrl(source.getImageUrl());
        accountEntity.setProvider(source.getProvider());
        accountEntity.setProviderId(source.getProviderId());
        accountEntity.setApiKey(source.getApiKey());
        accountEntity.setSecretKey(source.getSecretKey());
        return accountEntity;
    }
}
