package com.trader.core.users.services;

import com.trader.core.users.domain.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
