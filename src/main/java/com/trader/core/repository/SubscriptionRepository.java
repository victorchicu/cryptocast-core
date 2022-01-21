package com.trader.core.repository;

import com.trader.core.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionRepository {
}
