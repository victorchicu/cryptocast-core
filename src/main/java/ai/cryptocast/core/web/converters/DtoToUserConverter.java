package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.web.dto.SignUpDto;
import ai.cryptocast.core.enums.OAuth2Provider;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter implements Converter<SignUpDto, User> {
    private final PasswordEncoder passwordEncoder;

    public DtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SignUpDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .auth2Provider(OAuth2Provider.LOCAL)
                //TODO: Refactor providers and API credentials
                //.apiKey(source.getApiKey())
                //.secretKey(source.getSecretKey())
                //.exchangeProvider(source.getExchangeProvider())
                .build();
    }
}
