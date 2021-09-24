package com.crypto.core.binance.subscriptions.repository;

import com.crypto.core.binance.subscriptions.domain.Subscription;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface CustomSubscriptionsRepository {
    void updateSubscription(Query query, String propertyKey, Object propertyValue);

    void removeSubscriptions(String requesterId);

    List<Subscription> findSubscriptions(String requesterId);

    List<Subscription> findSubscriptions(String requesterId, List<String> symbolNames);

    Optional<Subscription> findSubscription(String requesterId, String symbolName);
}
