package com.crypto.core.auth.services;

import com.crypto.core.auth.domain.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findById(String id);
    Optional<Account> findByEmail(String email);
}
