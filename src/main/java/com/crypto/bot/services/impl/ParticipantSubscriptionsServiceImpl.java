package com.crypto.bot.services.impl;

import com.crypto.bot.repository.ParticipantsRepository;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import com.crypto.bot.services.ParticipantSubscriptionsService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantSubscriptionsServiceImpl implements ParticipantSubscriptionsService {
    private final ParticipantsRepository participantsRepository;

    public ParticipantSubscriptionsServiceImpl(ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        participantsRepository.deleteById(subscriptionId);
    }

    @Override
    public void deleteSubscriptions(List<String> ids) {
        participantsRepository.deleteAllById(ids);
    }

    @Override
    public void updateSubscription(Query query, String key, Object value) {
        participantsRepository.updateSubscription(query, key, value);
    }

    @Override
    public ParticipantSubscriptionEntity saveSubscription(ParticipantSubscriptionEntity message) {
        return participantsRepository.save(message);
    }

    @Override
    public List<ParticipantSubscriptionEntity> findSubscriptions(Integer participantId, List<String> symbols) {
        return participantsRepository.findSubscriptions(participantId, symbols);
    }

    @Override
    public List<ParticipantSubscriptionEntity> findAllSubscriptions() {
        return IterableUtils.toList(participantsRepository.findAll());
    }

    @Override
    public Optional<ParticipantSubscriptionEntity> findSubscription(Integer participantId, String symbol) {
        return participantsRepository.findSubscription(participantId, symbol);
    }
}
