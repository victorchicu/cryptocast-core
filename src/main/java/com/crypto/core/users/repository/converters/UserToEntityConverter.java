package com.crypto.core.users.repository.converters;

import com.crypto.core.users.domain.User;
import com.crypto.core.users.entity.UserEntity;
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
        return accountEntity;
    }
}
