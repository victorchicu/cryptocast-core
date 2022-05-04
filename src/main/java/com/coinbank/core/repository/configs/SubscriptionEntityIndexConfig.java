package com.coinbank.core.repository.configs;

import com.coinbank.core.entity.AssetTrackerEntity;
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
                .append(AssetTrackerEntity.Field.ASSET_NAME, 1)
        );
        mongoOperations.indexOps(AssetTrackerEntity.class)
                .ensureIndex(idx.named(AssetTrackerEntity.COLLECTION_NAME).unique().sparse());
    }
}
