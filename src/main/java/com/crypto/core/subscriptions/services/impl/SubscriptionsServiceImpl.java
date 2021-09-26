package com.crypto.core.subscriptions.services.impl;

import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.entity.SubscriptionEntity;
import com.crypto.core.subscriptions.repository.SubscriptionsRepository;
import com.crypto.core.subscriptions.services.SubscriptionsService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final ConversionService conversionService;
    private final SubscriptionsRepository subscriptionsRepository;

    public SubscriptionsServiceImpl(ConversionService conversionService, SubscriptionsRepository subscriptionsRepository) {
        this.conversionService = conversionService;
        this.subscriptionsRepository = subscriptionsRepository;
    }

    @Override
    public void deleteSubscriptionById(String id) {
        subscriptionsRepository.deleteById(id);
    }

    @Override
    public void deleteAllSubscriptions(Principal principal) {
        subscriptionsRepository.removeSubscriptions(principal.getName());
    }

    @Override
    public void deleteAllSubscriptionsById(List<String> list) {
        subscriptionsRepository.deleteAllById(list);
    }

    @Override
    public void updateSubscription(Query query, String updateKey, Object updateValue) {
        subscriptionsRepository.updateSubscription(query, updateKey, updateValue);
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = toSubscriptionEntity(subscription);
        subscription = toSubscription(subscriptionsRepository.save(subscriptionEntity));
        return subscription;
    }

    @Override
    public List<Subscription> findSubscriptions(Principal principal) {
        return subscriptionsRepository.findSubscriptions(principal.getName());
    }

    @Override
    public List<Subscription> findSubscriptions(Principal principal, List<String> symbolNames) {
        return subscriptionsRepository.findSubscriptions(principal.getName(), symbolNames);
    }

    @Override
    public List<Subscription> findAllSubscriptions() {
        List<SubscriptionEntity> subscriptions = IterableUtils.toList(subscriptionsRepository.findAll());
        return subscriptions.stream()
                .map(this::toSubscription)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Subscription> findSubscription(Principal principal, String symbolName) {
        return subscriptionsRepository.findSubscription(principal.getName(), symbolName);
    }

    private Subscription toSubscription(SubscriptionEntity subscriptionEntity) {
        return conversionService.convert(subscriptionEntity, Subscription.class);
    }

    private SubscriptionEntity toSubscriptionEntity(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionEntity.class);
    }
}
