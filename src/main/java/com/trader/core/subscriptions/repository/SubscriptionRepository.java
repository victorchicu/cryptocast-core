package com.trader.core.subscriptions.repository;

import com.trader.core.subscriptions.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionRepository {
}
