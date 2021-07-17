package com.crypto.core.repository.impl;

import com.crypto.core.repository.CustomSubscriptionsRepository;
import com.crypto.core.repository.entity.SubscriptionEntity;
import com.mongodb.client.result.DeleteResult;
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
    public DeleteResult removeSubscriptions(Integer participantId) {
        Query query = Query.query(
                Criteria.where(SubscriptionEntity.Field.PARTICIPANT_ID)
                .is(participantId)
        );
        return mongoOperations.remove(query, SubscriptionEntity.class, SubscriptionEntity.COLLECTION_NAME);
    }

    @Override
    public List<SubscriptionEntity> findSubscriptions(Integer participantId) {
        Query query = Query.query(
                Criteria.where(SubscriptionEntity.Field.PARTICIPANT_ID)
                        .is(participantId)
        );
        return mongoOperations.find(
                query,
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
    }

    @Override
    public List<SubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbolNames) {
        Query query = Query.query(
                Criteria.where(SubscriptionEntity.Field.PARTICIPANT_ID)
                        .is(participantId)
                        .and(SubscriptionEntity.Field.SYMBOL_NAME)
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
                Criteria.where(SubscriptionEntity.Field.PARTICIPANT_ID)
                        .is(participantId)
                        .and(SubscriptionEntity.Field.SYMBOL_NAME)
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
