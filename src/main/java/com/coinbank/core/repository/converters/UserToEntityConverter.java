package com.coinbank.core.repository.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.entity.UserEntity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(source.getEmail());
        userEntity.setPassword(source.getPassword());
        userEntity.setImageUrl(source.getImageUrl());
        userEntity.setProviderId(source.getProviderId());
        userEntity.setAuth2Provider(source.getAuth2Provider());
        Map<String, User.CryptoExchange> cryptoExchanges = MapUtils.emptyIfNull(source.getCryptoExchanges());
        userEntity.setCryptoExchanges(cryptoExchanges.entrySet().stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                valueMapper ->
                                        UserEntity.CryptoExchange.newBuilder()
                                                .name(valueMapper.getValue().getName())
                                                .apiKey(valueMapper.getValue().getApiKey())
                                                .secretKey(valueMapper.getValue().getSecretKey())
                                                .provider(valueMapper.getValue().getProvider())
                                                .build()
                        )
                ));
        return userEntity;
    }
}
