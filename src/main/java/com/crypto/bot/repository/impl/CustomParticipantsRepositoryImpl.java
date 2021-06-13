package com.crypto.bot.repository.impl;

import com.crypto.bot.repository.CustomParticipantsRepository;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomParticipantsRepositoryImpl implements CustomParticipantsRepository {
    private final MongoOperations mongoOperations;

    public CustomParticipantsRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols) {
        Query query = Query.query(Criteria.where("participantId").is(participantId).and("symbol").in(symbols));
        return mongoOperations.find(query, ParticipantSubscriptionEntity.class, "participant_subscriptions");
    }

    @Override
    public Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol) {
        Query query = Query.query(Criteria.where("participantId").is(participantId).and("symbol").is(symbol));
        ParticipantSubscriptionEntity participantSubscription = mongoOperations.findOne(query, ParticipantSubscriptionEntity.class, "participant_subscriptions");
        return Optional.ofNullable(participantSubscription);
    }
}
