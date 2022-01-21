package com.trader.core.services;

import com.trader.core.domain.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    void deleteSubscriptions(Principal principal);

    void deleteSubscriptionById(String id);

    Subscription saveSubscription(Subscription subscription);

    Page<Subscription> findSubscriptions(Principal principal, Pageable pageable);

    Page<Subscription> findSubscriptions(Principal principal, List<String> assetNames, Pageable pageable);

    Optional<Subscription> findSubscription(Principal principal, String assetName);
}
