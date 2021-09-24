package com.crypto.core.binance.subscriptions.services;

import com.crypto.core.binance.subscriptions.domain.Subscription;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionsService {
    void updateSubscription(Query query, String updateKey, Object updateValue);

    void deleteSubscription(String id);

    void deleteAllSubscriptions(String requesterId);

    void deleteAllSubscriptionsById(List<String> list);

    Subscription saveSubscription(Subscription subscription);

    List<Subscription> findSubscriptions(String requesterId);

    List<Subscription> findSubscriptions(String requesterId, List<String> symbolNames);

    List<Subscription> findAllSubscriptions();

    Optional<Subscription> findSubscription(String requesterId, String symbolName);

}
