package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;

import java.util.List;
import java.util.Optional;

public interface CustomParticipantsRepository {
    List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols);

    Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol);
}
