package com.crypto.core.watchlist.repository;

import com.crypto.core.watchlist.domain.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CustomWatchlistRepository {
    void removeWatchlsit(Principal principal);

    Page<Subscription> listSubscriptions(Principal principal, Pageable pageable);

    Page<Subscription> listSubscriptions(Principal principal, List<String> symbolNames, Pageable pageable);

    Optional<Subscription> findSubscription(Principal principal, String symbolName);
}
