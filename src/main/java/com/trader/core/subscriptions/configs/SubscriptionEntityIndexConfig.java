package com.trader.core.subscriptions.configs;

import com.trader.core.subscriptions.entity.SubscriptionEntity;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;

import javax.annotation.PostConstruct;

@Configuration
public class SubscriptionEntityIndexConfig {
    private final MongoOperations mongoOperations;

    public SubscriptionEntityIndexConfig(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @PostConstruct
    public void init() {
        mongoOperations.indexOps(SubscriptionEntity.class)
                .ensureIndex(new CompoundIndexDefinition(
                        new Document().append(SubscriptionEntity.Field.ASSET_NAME, 1))
                        .named(SubscriptionEntity.COLLECTION_NAME)
                        .unique()
                        .sparse()
                );
    }
}
