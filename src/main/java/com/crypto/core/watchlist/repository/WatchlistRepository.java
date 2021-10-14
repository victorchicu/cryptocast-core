package com.crypto.core.watchlist.repository;

import com.crypto.core.watchlist.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends CrudRepository<SubscriptionEntity, String>, CustomWatchlistRepository {
}
