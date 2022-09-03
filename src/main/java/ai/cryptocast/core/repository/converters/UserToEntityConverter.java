package ai.cryptocast.core.repository.converters;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.repository.entity.WalletEntity;
import ai.cryptocast.core.repository.entity.UserEntity;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        Map<String, WalletEntity> wallets = MapUtils.emptyIfNull(source.getWallets()).entrySet().stream()
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> WalletEntity.newBuilder()
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
                .wallets(wallets)
                .build();
    }
}
