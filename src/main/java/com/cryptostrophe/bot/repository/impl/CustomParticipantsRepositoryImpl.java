package com.cryptostrophe.bot.repository.impl;

import com.cryptostrophe.bot.repository.CustomParticipantsRepository;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
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
    public List<ParticipantSubscription> findSubscriptions(Integer participantId, String[] symbols) {
        Query query = Query.query(
                Criteria.where("participant_id")
                        .is(participantId)
                        .and("symbol")
                        .in(symbols)
        );
        return mongoOperations.find(query, ParticipantSubscription.class, "participant_subscriptions");
    }

    @Override
    public Optional<ParticipantSubscription> findSubscription(Integer participantId, String symbol) {
        Query query = Query.query(
                Criteria.where("symbol")
                        .is(symbol)
                        .and("participant_id")
                        .is(participantId)
        );
        ParticipantSubscription participantSubscription = mongoOperations.findOne(query, ParticipantSubscription.class, "participant_subscriptions");
        return Optional.ofNullable(participantSubscription);
    }
}