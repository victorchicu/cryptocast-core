package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.ParticipantSubscription;

import java.util.List;
import java.util.Optional;

public interface CustomParticipantsRepository {
    List<ParticipantSubscription> findSubscriptions(Integer participantId, String[] symbols);

    Optional<ParticipantSubscription> findSubscription(Integer participantId, String symbol);
}
