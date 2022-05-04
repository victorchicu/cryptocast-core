package com.coinbank.core.repository;

import com.coinbank.core.entity.UserEntity;
import com.coinbank.core.enums.ExchangeProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndExchangeProvider(String email, ExchangeProvider exchangeProvider);
}
