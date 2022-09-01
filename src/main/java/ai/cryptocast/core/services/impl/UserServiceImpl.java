package ai.cryptocast.core.services.impl;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.repository.UserRepository;
import ai.cryptocast.core.repository.entity.UserEntity;
import ai.cryptocast.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ConversionService conversionService;

    public UserServiceImpl(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return toUser(userEntity);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id)
                .map(this::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::toUser);
    }

    private User toUser(UserEntity accountEntity) {
        return conversionService.convert(accountEntity, User.class);
    }

    private UserEntity toUserEntity(User user) {
        return conversionService.convert(user, UserEntity.class);
    }
}
