package com.coinbank.core.services.impl;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.domain.User;
import com.coinbank.core.entity.AssetTrackerEntity;
import com.coinbank.core.repository.AssetTrackerRepository;
import com.coinbank.core.services.AssetTrackerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetTrackerServiceImpl implements AssetTrackerService {
    private final ConversionService conversionService;
    private final AssetTrackerRepository assetTrackerRepository;

    public AssetTrackerServiceImpl(ConversionService conversionService, AssetTrackerRepository assetTrackerRepository) {
        this.conversionService = conversionService;
        this.assetTrackerRepository = assetTrackerRepository;
    }

    @Override
    public void deleteAll(User user) {
        assetTrackerRepository.removeSubscriptions(user);
    }

    @Override
    public void deleteById(String subscriptionId) {
        assetTrackerRepository.deleteById(subscriptionId);
    }

    @Override
    public AssetTracker save(AssetTracker assetTracker) {
        AssetTrackerEntity assetTrackerEntity = toSubscriptionEntity(assetTracker);
        assetTracker = toSubscription(assetTrackerRepository.save(assetTrackerEntity));
        return assetTracker;
    }

    @Override
    public Page<AssetTracker> findAll(User user, Pageable pageable) {
        return assetTrackerRepository.listSubscriptions(user, pageable);
    }

    @Override
    public Page<AssetTracker> findAll(User user, List<String> assetNames, Pageable pageable) {
        return assetTrackerRepository.listSubscriptions(user, assetNames, pageable);
    }

    @Override
    public Optional<AssetTracker> findByAssetName(User user, String assetName) {
        return assetTrackerRepository.findSubscription(user, assetName);
    }


    private AssetTracker toSubscription(AssetTrackerEntity assetTrackerEntity) {
        return conversionService.convert(assetTrackerEntity, AssetTracker.class);
    }

    private AssetTrackerEntity toSubscriptionEntity(AssetTracker assetTracker) {
        return conversionService.convert(assetTracker, AssetTrackerEntity.class);
    }
}
