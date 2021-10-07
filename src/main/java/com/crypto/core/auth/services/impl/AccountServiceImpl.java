package com.crypto.core.auth.services.impl;

import com.crypto.core.auth.domain.Account;
import com.crypto.core.auth.repository.AccountRepository;
import com.crypto.core.auth.repository.entity.AccountEntity;
import com.crypto.core.auth.services.AccountService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private ConversionService conversionService;

    public AccountServiceImpl(AccountRepository accountRepository, ConversionService conversionService) {
        this.accountRepository = accountRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = toAccountEntity(account);
        accountEntity = accountRepository.save(accountEntity);
        return toAccount(accountEntity);
    }

    @Override
    public Optional<Account> findById(String id) {
        return accountRepository.findById(id)
                .map(this::toAccount);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findAccountEntityByEmail(email)
                .map(this::toAccount);
    }

    private Account toAccount(AccountEntity accountEntity) {
        return conversionService.convert(accountEntity, Account.class);
    }

    private AccountEntity toAccountEntity(Account account) {
        return conversionService.convert(account, AccountEntity.class);
    }
}
