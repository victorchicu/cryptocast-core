package com.crypto.core.watchlist.repository.impl;

import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.entity.SubscriptionEntity;
import com.crypto.core.watchlist.repository.CustomWatchlistRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomWatchlistRepositoryImpl implements CustomWatchlistRepository {
    private final MongoOperations mongoOperations;
    private final ConversionService conversionService;

    public CustomWatchlistRepositoryImpl(MongoOperations mongoOperations, ConversionService conversionService) {
        this.mongoOperations = mongoOperations;
        this.conversionService = conversionService;
    }

    @Override
    public void removeWatchlsit(Principal principal) {
        mongoOperations.remove(
                Query.query(
                        Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                                .is(principal.getName())
                ),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );
    }

    @Override
    public Page<Subscription> listSubscriptions(Principal principal, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY).is(principal.getName());

        List<SubscriptionEntity> watchlist = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                watchlist.stream().map(this::toWatchlist).collect(Collectors.toList()),
                pageable,
                () -> 0
        );
    }

    @Override
    public Page<Subscription> listSubscriptions(Principal principal, List<String> assetNames, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                .is(principal.getName())
                .and(SubscriptionEntity.Field.ASSET_NAME)
                .in(assetNames);

        List<SubscriptionEntity> watchlistEntities = mongoOperations.find(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                watchlistEntities.stream().map(this::toWatchlist).collect(Collectors.toList()),
                pageable, () -> 0
        );
    }

    @Override
    public Optional<Subscription> findSubscription(Principal principal, String assetName) {
        Criteria matchCriteria = Criteria.where(SubscriptionEntity.Field.CREATED_BY)
                .is(principal.getName())
                .and(SubscriptionEntity.Field.ASSET_NAME)
                .is(assetName);

        SubscriptionEntity subscriptionEntity = mongoOperations.findOne(
                Query.query(matchCriteria),
                SubscriptionEntity.class,
                SubscriptionEntity.COLLECTION_NAME
        );

        return Optional.ofNullable(subscriptionEntity).map(this::toWatchlist);
    }


    private Subscription toWatchlist(SubscriptionEntity subscriptionEntity) {
        return conversionService.convert(subscriptionEntity, Subscription.class);
    }
}
