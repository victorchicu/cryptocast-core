package com.crypto.bot.repository.impl;

import com.crypto.bot.repository.CustomSubscriptionsRepository;
import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomSubscriptionsRepositoryImpl implements CustomSubscriptionsRepository {
    private final MongoOperations mongoOperations;

    public CustomSubscriptionsRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public UpdateResult updateSubscription(Query query, String propertyKey, Object propertyValue) {
        return mongoOperations.updateFirst(
                query,
                Update.update(propertyKey, propertyValue),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
    }

    @Override
    public List<SubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbolNames) {
        Query query = Query.query(
                Criteria.where("participantId")
                        .is(participantId)
                        .and("symbolName")
                        .in(symbolNames)
        );
        return mongoOperations.find(
                query,
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
    }

    @Override
    public Optional<SubscriptionEntity> findSubscription(Integer participantId, String symbolName) {
        Query query = Query.query(
                Criteria.where("participantId")
                        .is(participantId)
                        .and("symbolName")
                        .is(symbolName)
        );
        SubscriptionEntity participantSubscription = mongoOperations.findOne(
                query,
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
        return Optional.ofNullable(participantSubscription);
    }
}
