package com.crypto.core.watchlist.configs;

import com.crypto.core.watchlist.entity.WatchlistEntity;
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
        mongoOperations.indexOps(WatchlistEntity.class)
                .ensureIndex(new CompoundIndexDefinition(
                        new Document().append(WatchlistEntity.Field.SYMBOL_NAME, 1))
                        .named(WatchlistEntity.COLLECTION_NAME)
                        .unique()
                        .sparse()
                );
    }
}
