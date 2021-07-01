package com.crypto.bot.repository;

import com.crypto.bot.repository.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends CrudRepository<SubscriptionEntity, String>, CustomSubscriptionsRepository {
}
