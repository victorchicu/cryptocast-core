package com.crypto.bot.services;

import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionsService {
    void deleteSubscription(String subscriptionId);

    void deleteAllSubscriptions(Integer participantId);

    void deleteAllSubscriptionsById(List<String> list);

    UpdateResult updateSubscription(Query query, String updateKey, Object updateValue);

    SubscriptionEntity saveSubscription(SubscriptionEntity subscription);

    List<SubscriptionEntity> findSubscriptions(Integer participantId);

    List<SubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbolNames);

    List<SubscriptionEntity> findAllSubscriptions();

    Optional<SubscriptionEntity> findSubscription(Integer participantId, String symbolName);

}
