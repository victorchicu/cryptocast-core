package com.coinbank.core.repository;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomSubscriptionRepository {
    void removeSubscriptions(User user);

    Page<AssetTracker> listSubscriptions(User user, Pageable pageable);

    Page<AssetTracker> listSubscriptions(User user, List<String> symbolNames, Pageable pageable);

    Optional<AssetTracker> findSubscription(User user, String symbolName);
}
