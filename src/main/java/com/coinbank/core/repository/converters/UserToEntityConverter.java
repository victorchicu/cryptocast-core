package com.coinbank.core.repository.converters;

import com.coinbank.core.domain.User;
import com.coinbank.core.repository.entity.ApiKeyEntity;
import com.coinbank.core.repository.entity.UserEntity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        Map<String, ApiKeyEntity> exchanges = MapUtils.emptyIfNull(source.getExchanges()).entrySet().stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> ApiKeyEntity.newBuilder()
                                        .name(entry.getValue().getLabel())
                                        .apiKey(entry.getValue().getApiKey())
                                        .secretKey(entry.getValue().getSecretKey())
                                        .exchange(entry.getValue().getExchange())
                                        .build()
                        )
                );
        return UserEntity.newBuilder()
                .id(source.getId())
                .email(source.getEmail())
                .password(source.getPassword())
                .imageUrl(source.getImageUrl())
                .providerId(source.getProviderId())
                .auth2Provider(source.getAuth2Provider())
                .apiKeys(exchanges)
                .build();
    }
}
