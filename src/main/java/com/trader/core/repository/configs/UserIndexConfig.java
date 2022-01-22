package com.trader.core.repository.configs;

import com.trader.core.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;

@Configuration
public class UserIndexConfig {
    private final MongoOperations mongoOperations;

    public UserIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        Index idx = new Index().on(
                buildIndexKeyName(UserEntity.Field.EMAIL, UserEntity.Field.EXCHANGE_PROVIDER),
                Sort.Direction.ASC
        );
        mongoOperations.indexOps(UserEntity.class).ensureIndex(idx.unique().sparse());
    }

    private String buildIndexKeyName(String ...keys) {
        return String.join(".", keys);
    }
}
