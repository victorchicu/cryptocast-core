package com.coinbank.core.services;

import com.coinbank.core.domain.User;
import com.coinbank.core.enums.ExchangeProvider;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
