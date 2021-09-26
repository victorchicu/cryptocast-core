package com.crypto.core.subscriptions.services;

import com.crypto.core.subscriptions.domain.Subscription;
import org.springframework.data.mongodb.core.query.Query;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface SubscriptionsService {
    void updateSubscription(Query query, String updateKey, Object updateValue);

    void deleteSubscriptionById(String id);

    void deleteAllSubscriptions(Principal principal);

    void deleteAllSubscriptionsById(List<String> list);

    Subscription saveSubscription(Subscription subscription);

    List<Subscription> findSubscriptions(Principal principal);

    List<Subscription> findSubscriptions(Principal principal, List<String> symbolNames);

    List<Subscription> findAllSubscriptions();

    Optional<Subscription> findSubscription(Principal principal, String symbolName);
}
