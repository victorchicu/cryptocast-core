package com.crypto.core.auth.repository;

import com.crypto.core.auth.repository.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    Optional<AccountEntity> findAccountEntityByEmail(String email);
}
