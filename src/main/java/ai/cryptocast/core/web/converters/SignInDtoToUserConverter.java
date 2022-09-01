package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.web.dto.SignInDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignInDtoToUserConverter implements Converter<SignInDto, User> {
    private final PasswordEncoder passwordEncoder;

    public SignInDtoToUserConverter(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(SignInDto source) {
        return User.newBuilder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .build();
    }
}
