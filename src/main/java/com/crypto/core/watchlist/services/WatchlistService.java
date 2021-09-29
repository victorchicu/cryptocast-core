package com.crypto.core.watchlist.services;

import com.crypto.core.watchlist.domain.Watchlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface WatchlistService {
    void update(Query query, String updateKey, Object updateValue);

    void deleteAll(Principal principal);

    void deleteById(String id);

    void deleteAllById(List<String> list);

    Watchlist save(Watchlist watchlist);

    Page<Watchlist> findAll(Principal principal, Pageable pageable);

    Page<Watchlist> findAll(Principal principal, List<String> symbolNames, Pageable pageable);

    Page<Watchlist> findAll(Pageable pageable);

    Optional<Watchlist> find(Principal principal, String symbolName);
}
