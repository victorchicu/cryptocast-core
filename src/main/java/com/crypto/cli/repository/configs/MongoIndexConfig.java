package com.crypto.cli.repository.configs;

import com.crypto.cli.repository.entity.SubscriptionEntity;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
public class MongoIndexConfig {
    private final MongoOperations mongoOperations;

    public MongoIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        mongoOperations.indexOps(SubscriptionEntity.class)
                .ensureIndex(new CompoundIndexDefinition(
                        new Document()
                                .append(SubscriptionEntity.Field.SYMBOL_NAME, 1)
                                .append(SubscriptionEntity.Field.PARTICIPANT_ID, 1)
                        )
                        .named(SubscriptionEntity.COLLECTION_NAME)
                        .unique()
                        .sparse()
                );
    }
}
