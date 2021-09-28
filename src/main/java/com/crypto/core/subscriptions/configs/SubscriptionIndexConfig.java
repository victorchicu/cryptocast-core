package com.crypto.core.subscriptions.configs;

import com.crypto.core.subscriptions.entity.SubscriptionEntity;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
public class SubscriptionIndexConfig {
    private final MongoOperations mongoOperations;

    public SubscriptionIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        mongoOperations.indexOps(SubscriptionEntity.class)
                .ensureIndex(new CompoundIndexDefinition(
                        new Document().append(SubscriptionEntity.Field.SYMBOL_NAME, 1))
                        .named(SubscriptionEntity.COLLECTION_NAME)
                        .unique()
                        .sparse()
                );
    }
}
