package com.crypto.core.watchlist.repository;

import com.crypto.core.watchlist.domain.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CustomWatchlistRepository {
    void update(Query query, String propertyKey, Object propertyValue);

    void remove(Principal principal);

    Page<Watchlist> list(Principal principal, Pageable pageable);

    Page<Watchlist> list(Principal principal, List<String> symbolNames, Pageable pageable);

    Optional<Watchlist> find(Principal principal, String symbolName);
}
