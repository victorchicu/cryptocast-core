package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.repository.ParticipantsRepository;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.cryptostrophe.bot.services.ParticipantSubscriptionsService;
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
    public void deleteSubscriptions(List<String> ids) {
        participantsRepository.deleteAllById(ids);
    }

    @Override
    public ParticipantSubscription saveSubscription(ParticipantSubscription message) {
        return participantsRepository.save(message);
    }

    @Override
    public List<ParticipantSubscription> findSubscriptions(Integer participantId, List<String> symbols) {
        return participantsRepository.findSubscriptions(participantId, symbols);
    }

    @Override
    public Optional<ParticipantSubscription> findSubscription(Integer participantId, String symbol) {
        return participantsRepository.findSubscription(participantId, symbol);
    }
}