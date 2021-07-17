package com.crypto.cli.repository;

import com.crypto.cli.repository.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionsRepository {
}
