package com.crypto.core.users.services.impl;

import com.crypto.core.users.domain.User;
import com.crypto.core.users.repository.UserRepository;
import com.crypto.core.users.entity.UserEntity;
import com.crypto.core.users.services.UserService;
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
        UserEntity accountEntity = toAccountEntity(user);
        accountEntity = userRepository.save(accountEntity);
        return toAccount(accountEntity);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id)
                .map(this::toAccount);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findAccountEntityByEmail(email)
                .map(this::toAccount);
    }


    private User toAccount(UserEntity accountEntity) {
        return conversionService.convert(accountEntity, User.class);
    }

    private UserEntity toAccountEntity(User user) {
        return conversionService.convert(user, UserEntity.class);
    }
}
