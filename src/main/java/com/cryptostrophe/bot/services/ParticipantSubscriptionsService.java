package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.ParticipantSubscription;

import java.util.List;
import java.util.Optional;

public interface ParticipantSubscriptionsService {
    void deleteSubscriptions(List<String> ids);

    ParticipantSubscription saveSubscription(ParticipantSubscription message);

    List<ParticipantSubscription> findSubscriptions(Integer participantId, String[] symbols);

    Optional<ParticipantSubscription> findSubscription(Integer participantId, String symbol);
}
