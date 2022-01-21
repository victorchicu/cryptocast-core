package com.trader.core.services.impl;

import com.trader.core.domain.Subscription;
import com.trader.core.entity.SubscriptionEntity;
import com.trader.core.repository.SubscriptionRepository;
import com.trader.core.services.SubscriptionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final ConversionService conversionService;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(ConversionService conversionService, SubscriptionRepository subscriptionRepository) {
        this.conversionService = conversionService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void deleteSubscriptions(Principal principal) {
        subscriptionRepository.removeSubscriptions(principal);
    }

    @Override
    public void deleteSubscriptionById(String subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = toSubscriptionEntity(subscription);
        subscription = toSubscription(subscriptionRepository.save(subscriptionEntity));
        return subscription;
    }

    @Override
    public Page<Subscription> findSubscriptions(Principal principal, Pageable pageable) {
        return subscriptionRepository.listSubscriptions(principal, pageable);
    }

    @Override
    public Page<Subscription> findSubscriptions(Principal principal, List<String> assetNames, Pageable pageable) {
        return subscriptionRepository.listSubscriptions(principal, assetNames, pageable);
    }

    @Override
    public Optional<Subscription> findSubscription(Principal principal, String assetName) {
        return subscriptionRepository.findSubscription(principal, assetName);
    }


    private Subscription toSubscription(SubscriptionEntity subscriptionEntity) {
        return conversionService.convert(subscriptionEntity, Subscription.class);
    }

    private SubscriptionEntity toSubscriptionEntity(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionEntity.class);
    }
}
