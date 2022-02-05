package com.trader.core.services;

import com.trader.core.domain.Subscription;
import com.trader.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    void deleteSubscriptions(User user);

    void deleteSubscriptionById(String id);

    Subscription saveSubscription(Subscription subscription);

    Page<Subscription> findSubscriptions(User user, Pageable pageable);

    Page<Subscription> findSubscriptions(User user, List<String> assetNames, Pageable pageable);

    Optional<Subscription> findSubscription(User user, String assetName);
}
