package com.crypto.core.auth.repository.configs;

import com.crypto.core.auth.repository.entity.AccountEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;

@Configuration
public class AccountIndexConfig {
    private final MongoOperations mongoOperations;

    public AccountIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        mongoOperations.indexOps(AccountEntity.class)
                .ensureIndex(
                        new Index(AccountEntity.Field.EMAIL, Sort.Direction.ASC).sparse().unique()
                );
    }
}
