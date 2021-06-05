package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;

import java.util.List;
import java.util.Optional;

public interface ParticipantSubscriptionsService {
    void deleteSubscription(String subscriptionId);

    void deleteSubscriptions(List<String> ids);

    ParticipantSubscriptionEntity saveSubscription(ParticipantSubscriptionEntity message);

    List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols);

    List<ParticipantSubscriptionEntity> findAllSubscriptions();

    Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol);

}
