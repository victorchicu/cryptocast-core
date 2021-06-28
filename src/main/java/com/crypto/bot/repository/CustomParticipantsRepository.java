package com.crypto.bot.repository;

import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;

public interface CustomParticipantsRepository {
    void updateSubscription(Query query, String key, Object value);

    List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols);

    Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol);

}
