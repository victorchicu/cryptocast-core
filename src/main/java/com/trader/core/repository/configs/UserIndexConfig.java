package com.trader.core.repository.configs;

import com.trader.core.entity.UserEntity;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
public class UserIndexConfig {
    private final MongoOperations mongoOperations;

    public UserIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        CompoundIndexDefinition idx = new CompoundIndexDefinition(
                new Document()
                        .append(UserEntity.Field.EMAIL, 1)
                        .append(UserEntity.Field.EXCHANGE_PROVIDER, 1)
        );
        mongoOperations.indexOps(UserEntity.class).ensureIndex(idx.sparse().unique());
    }
}
