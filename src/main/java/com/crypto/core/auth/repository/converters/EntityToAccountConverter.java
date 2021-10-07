package com.crypto.core.auth.repository.converters;

import com.crypto.core.auth.domain.Account;
import com.crypto.core.auth.repository.entity.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToAccountConverter implements Converter<AccountEntity, Account> {
    @Override
    public Account convert(AccountEntity source) {
        return Account.newBuilder()
                .email(source.getEmail())
                .password(source.getPassword())
                .build();
    }
}
