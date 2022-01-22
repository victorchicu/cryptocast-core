package com.trader.core.services;

import com.trader.core.domain.User;
import com.trader.core.enums.ExchangeProvider;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndExchangeProvider(String email, ExchangeProvider exchangeProvider);
}
