package com.crypto.bot.services.impl;

import com.crypto.bot.repository.SubscriptionsRepository;
import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.crypto.bot.services.SubscriptionsService;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final SubscriptionsRepository subscriptionsRepository;

    public SubscriptionsServiceImpl(SubscriptionsRepository subscriptionsRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        subscriptionsRepository.deleteById(subscriptionId);
    }

    @Override
    public void deleteAllSubscriptions(Integer participantId) {
        subscriptionsRepository.removeSubscriptions(participantId);
    }

    @Override
    public void deleteAllSubscriptionsById(List<String> list) {
        subscriptionsRepository.deleteAllById(list);
    }

    @Override
    public UpdateResult updateSubscription(Query query, String updateKey, Object updateValue) {
        return subscriptionsRepository.updateSubscription(query, updateKey, updateValue);
    }

    @Override
    public SubscriptionEntity saveSubscription(SubscriptionEntity subscription) {
        return subscriptionsRepository.save(subscription);
    }

    @Override
    public List<SubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbolNames) {
        return subscriptionsRepository.findSubscriptions(participantId, symbolNames);
    }

    @Override
    public List<SubscriptionEntity> findAllSubscriptions() {
        return IterableUtils.toList(subscriptionsRepository.findAll());
    }

    @Override
    public Optional<SubscriptionEntity> findSubscription(Integer participantId, String symbolName) {
        return subscriptionsRepository.findSubscription(participantId, symbolName);
    }
}
