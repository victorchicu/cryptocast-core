package com.crypto.core.binance.subscriptions.repository;

import com.crypto.core.binance.subscriptions.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionsRepository {
}
