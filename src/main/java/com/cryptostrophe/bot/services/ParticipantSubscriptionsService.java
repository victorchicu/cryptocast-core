package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.ParticipantSubscription;

import java.util.List;
import java.util.Optional;

public interface ParticipantSubscriptionsService {
    void deleteSubscription(String subscriptionId);

    void deleteSubscriptions(List<String> ids);

    ParticipantSubscription saveSubscription(ParticipantSubscription message);

    List<ParticipantSubscription> findSubscriptions(Integer participantId, List<String> symbols);

    List<ParticipantSubscription> findAllSubscriptions();

    Optional<ParticipantSubscription> findSubscription(Integer participantId, String symbol);

}
