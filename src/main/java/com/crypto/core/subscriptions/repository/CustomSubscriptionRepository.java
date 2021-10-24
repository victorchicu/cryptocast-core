package com.crypto.core.subscriptions.repository;

import com.crypto.core.subscriptions.domain.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CustomSubscriptionRepository {
    void removeSubscriptions(Principal principal);

    Page<Subscription> listSubscriptions(Principal principal, Pageable pageable);

    Page<Subscription> listSubscriptions(Principal principal, List<String> symbolNames, Pageable pageable);

    Optional<Subscription> findSubscription(Principal principal, String symbolName);
}
