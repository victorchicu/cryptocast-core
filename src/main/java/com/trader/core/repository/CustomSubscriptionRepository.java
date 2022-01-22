package com.trader.core.repository;

import com.trader.core.domain.Subscription;
import com.trader.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomSubscriptionRepository {
    void removeSubscriptions(User user);

    Page<Subscription> listSubscriptions(User user, Pageable pageable);

    Page<Subscription> listSubscriptions(User user, List<String> symbolNames, Pageable pageable);

    Optional<Subscription> findSubscription(User user, String symbolName);
}
