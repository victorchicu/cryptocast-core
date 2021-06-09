package com.cryptostrophe.bot.repository.configs;

import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
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
        mongoOperations.indexOps(ParticipantSubscriptionEntity.class)
                .ensureIndex(
                        new CompoundIndexDefinition(
                                new Document()
                                        .append("participantId", 1)
                                        .append("symbol", 1)
                        ).unique().sparse().named("participant_subscription")
                );

        mongoOperations.indexOps(SymbolTickerEventEntity.class)
                .ensureIndex(
                        new CompoundIndexDefinition(
                                new Document()
                                        .append("participantId", 1)
                                        .append("symbol", 1)
                        ).unique().sparse().named("symbol_ticker_event")
                );
    }
}
