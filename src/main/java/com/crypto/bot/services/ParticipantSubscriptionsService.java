package com.crypto.bot.services;

import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipantSubscriptionsService {
    void deleteSubscription(String subscriptionId);

    void deleteSubscriptions(List<String> ids);

    void updateSubscription(Query query, String key, Object value);

    ParticipantSubscriptionEntity saveSubscription(ParticipantSubscriptionEntity message);

    List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols);

    List<ParticipantSubscriptionEntity> findAllSubscriptions();

    Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol);

}
