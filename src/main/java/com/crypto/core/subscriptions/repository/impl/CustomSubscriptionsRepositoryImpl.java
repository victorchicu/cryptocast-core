package com.crypto.core.subscriptions.repository.impl;

import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.repository.CustomSubscriptionsRepository;
import com.crypto.core.subscriptions.entity.SubscriptionEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomSubscriptionsRepositoryImpl implements CustomSubscriptionsRepository {
    private final MongoOperations mongoOperations;
    private final ConversionService conversionService;

    public CustomSubscriptionsRepositoryImpl(MongoOperations mongoOperations, ConversionService conversionService) {
        this.mongoOperations = mongoOperations;
        this.conversionService = conversionService;
    }

    @Override
    public void removeSubscriptions(String requesterId) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.REQUESTER_ID).is(requesterId);
        mongoOperations.remove(Query.query(matchCriteria), SubscriptionEntity.class, SubscriptionEntity.COLLECTION_NAME);
    }

    @Override
    public void updateSubscription(Query query, String propertyKey, Object propertyValue) {
        mongoOperations.updateFirst(query, Update.update(propertyKey, propertyValue), SubscriptionEntity.class, SubscriptionEntity.COLLECTION_NAME);
    }

    @Override
    public List<Subscription> findSubscriptions(String requesterId) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.REQUESTER_ID).is(requesterId);

        List<SubscriptionEntity> subscriptions = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return subscriptions.stream().map(this::toSubscription).collect(Collectors.toList());
    }

    @Override
    public List<Subscription> findSubscriptions(String requesterId, List<String> symbolNames) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.REQUESTER_ID)
                .is(requesterId)
                .and(SubscriptionEntity.Field.SYMBOL_NAME)
                .in(symbolNames);

        List<SubscriptionEntity> subscriptions = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return subscriptions.stream().map(this::toSubscription).collect(Collectors.toList());
    }

    @Override
    public Optional<Subscription> findSubscription(String requesterId, String symbolName) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.REQUESTER_ID)
                .is(requesterId)
                .and(SubscriptionEntity.Field.SYMBOL_NAME)
                .is(symbolName);

        SubscriptionEntity subscriptionEntity = mongoOperations.findOne(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return Optional.ofNullable(subscriptionEntity).map(this::toSubscription);
    }


    private Subscription toSubscription(SubscriptionEntity subscriptionEntity) {
        return conversionService.convert(subscriptionEntity, Subscription.class);
    }
}
