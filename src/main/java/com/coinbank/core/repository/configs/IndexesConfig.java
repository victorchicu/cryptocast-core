package com.coinbank.core.repository.configs;

import com.coinbank.core.repository.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;

@Configuration
public class IndexesConfig {
    private final MongoOperations mongoOperations;

    public IndexesConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        Index emailIndex = new Index()
                .on(
                        buildIndexKeyName(UserEntity.Field.EMAIL),
                        Sort.Direction.ASC
                );
        mongoOperations.indexOps(UserEntity.class)
                .ensureIndex(
                        emailIndex.unique().sparse()
                );
    }

    private String buildIndexKeyName(String ...keys) {
        return String.join(".", keys);
    }
}
