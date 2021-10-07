package com.crypto.core.auth.repository.converters;

import com.crypto.core.auth.domain.Account;
import com.crypto.core.auth.repository.entity.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToEntityConverter implements Converter<Account, AccountEntity> {
    @Override
    public AccountEntity convert(Account source) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail(source.getEmail());
        accountEntity.setPassword(source.getPassword());
        return accountEntity;
    }
}
