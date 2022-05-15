package com.coinbank.core.repository.impl;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.domain.User;
import com.coinbank.core.repository.entity.AssetTrackerEntity;
import com.coinbank.core.repository.entity.BaseEntity;
import com.coinbank.core.repository.CustomSubscriptionRepository;
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
                        Criteria.where(BaseEntity.Field.CREATED_BY)
                                .is(user.getId())
                ),
                AssetTrackerEntity.class,
                AssetTrackerEntity.COLLECTION_NAME
        );
    }

    @Override
    public Page<AssetTracker> listSubscriptions(User user, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(BaseEntity.Field.CREATED_BY).is(user.getId());

        List<AssetTrackerEntity> subscriptions = mongoOperations.find(
                Query.query(matchCriteria),
                AssetTrackerEntity.class,
                AssetTrackerEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                subscriptions.stream().map(this::toSubscription).collect(Collectors.toList()),
                pageable,
                () -> 0
        );
    }

    @Override
    public Page<AssetTracker> listSubscriptions(User user, List<String> assetNames, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(BaseEntity.Field.CREATED_BY)
                .is(user.getId())
                .and(AssetTrackerEntity.Field.ASSET_NAME)
                .in(assetNames);

        List<AssetTrackerEntity> list = mongoOperations.find(
                Query.query(matchCriteria),
                AssetTrackerEntity.class,
                AssetTrackerEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                list.stream().map(this::toSubscription).collect(Collectors.toList()),
                pageable, () -> 0
        );
    }

    @Override
    public Optional<AssetTracker> findSubscription(User user, String assetName) {
        Criteria matchCriteria = Criteria.where(BaseEntity.Field.CREATED_BY)
                .is(user.getId())
                .and(AssetTrackerEntity.Field.ASSET_NAME)
                .is(assetName);

        AssetTrackerEntity assetTrackerEntity = mongoOperations.findOne(
                Query.query(matchCriteria),
                AssetTrackerEntity.class,
                AssetTrackerEntity.COLLECTION_NAME
        );

        return Optional.ofNullable(assetTrackerEntity).map(this::toSubscription);
    }


    private AssetTracker toSubscription(AssetTrackerEntity assetTrackerEntity) {
        return conversionService.convert(assetTrackerEntity, AssetTracker.class);
    }
}
