package com.crypto.core.repository;

import com.crypto.core.repository.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionsRepository {
}
