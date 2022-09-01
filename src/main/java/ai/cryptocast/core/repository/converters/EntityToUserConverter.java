package ai.cryptocast.core.repository.converters;

import ai.cryptocast.core.domain.ApiKey;
import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.repository.entity.UserEntity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EntityToUserConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity source) {
        Map<String, ApiKey> exchanges = MapUtils.emptyIfNull(source.getApiKeys()).entrySet().stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> ApiKey.newBuilder()
                                        .label(entry.getValue().getName())
                                        .apiKey(entry.getValue().getApiKey())
                                        .secretKey(entry.getValue().getSecretKey())
                                        .exchange(entry.getValue().getExchange())
                                        .build()
                        )
                );
        return User.newBuilder()
                .id(source.getId())
                .email(source.getEmail())
                .password(source.getPassword())
                .imageUrl(source.getImageUrl())
                .providerId(source.getProviderId())
                .auth2Provider(source.getAuth2Provider())
                .exchanges(exchanges)
                .build();
    }
}
