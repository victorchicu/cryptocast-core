package com.coinbank.core.repository.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.entity.UserEntity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EntityToUserConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity source) {
        Map<String, UserEntity.CryptoExchange> cryptoExchanges = MapUtils.emptyIfNull(source.getCryptoExchanges());
        return User.newBuilder()
                .id(source.getId())
                .email(source.getEmail())
                .password(source.getPassword())
                .imageUrl(source.getImageUrl())
                .providerId(source.getProviderId())
                .auth2Provider(source.getAuth2Provider())
                .cryptoExchanges(cryptoExchanges.entrySet().stream()
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        valueMapper ->
                                                User.CryptoExchange.newBuilder()
                                                        .name(valueMapper.getValue().getName())
                                                        .apiKey(valueMapper.getValue().getApiKey())
                                                        .secretKey(valueMapper.getValue().getSecretKey())
                                                        .provider(valueMapper.getValue().getProvider())
                                                        .build()
                                )
                        ))
                .build();
    }
}
