package com.trader.core.repository.configs;

import com.trader.core.entity.SubscriptionEntity;
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
        CompoundIndexDefinition idx = new CompoundIndexDefinition(new Document()
                .append(SubscriptionEntity.Field.ASSET_NAME, 1)
        );
        mongoOperations.indexOps(SubscriptionEntity.class)
                .ensureIndex(idx.named(SubscriptionEntity.COLLECTION_NAME).unique().sparse());
    }
}
