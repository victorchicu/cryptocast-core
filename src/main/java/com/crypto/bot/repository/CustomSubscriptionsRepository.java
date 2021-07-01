package com.crypto.bot.repository;

import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface CustomSubscriptionsRepository {
    UpdateResult updateSubscription(Query query, String propertyKey, Object propertyValue);

    List<SubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbolNames);

    Optional<SubscriptionEntity> findSubscription(Integer participantId, String symbolName);
}
