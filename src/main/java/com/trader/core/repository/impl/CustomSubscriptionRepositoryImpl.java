package com.trader.core.repository.impl;

import com.trader.core.domain.Subscription;
import com.trader.core.domain.User;
import com.trader.core.entity.SubscriptionEntity;
import com.trader.core.repository.CustomSubscriptionRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomSubscriptionRepositoryImpl implements CustomSubscriptionRepository {
    private final MongoOperations mongoOperations;
    private final ConversionService conversionService;

    public CustomSubscriptionRepositoryImpl(MongoOperations mongoOperations, ConversionService conversionService) {
        this.mongoOperations = mongoOperations;
        this.conversionService = conversionService;
    }

    @Override
    public void removeSubscriptions(User user) {
        mongoOperations.remove(
                Query.query(
                        Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                                .is(user.getId())
                ),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
    }

    @Override
    public Page<Subscription> listSubscriptions(User user, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY).is(user.getId());

        List<SubscriptionEntity> subscriptions = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                subscriptions.stream().map(this::toSubscription).collect(Collectors.toList()),
                pageable,
                () -> 0
        );
    }

    @Override
    public Page<Subscription> listSubscriptions(User user, List<String> assetNames, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                .is(user.getId())
                .and(SubscriptionEntity.Field.ASSET_NAME)
                .in(assetNames);

        List<SubscriptionEntity> list = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                list.stream().map(this::toSubscription).collect(Collectors.toList()),
                pageable, () -> 0
        );
    }

    @Override
    public Optional<Subscription> findSubscription(User user, String assetName) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                .is(user.getId())
                .and(SubscriptionEntity.Field.ASSET_NAME)
                .is(assetName);

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
