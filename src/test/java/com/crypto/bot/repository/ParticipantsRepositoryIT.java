package com.crypto.bot.repository;

import com.crypto.bot.BaseTest;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipantsRepositoryIT extends BaseTest {
    @Test
    public void should_save_and_find_subscription_by_participant_id_and_symbol() {
        ParticipantSubscriptionEntity expectedParticipantSubscription = participantsRepository.save(
                randomParticipantSubscription()
        );

        Optional<ParticipantSubscriptionEntity> actualParticipantSubscription = participantsRepository.findSubscription(
                expectedParticipantSubscription.getParticipantId(),
                expectedParticipantSubscription.getSymbol()
        );

        Assertions.assertEquals(
                expectedParticipantSubscription.getId(),
                actualParticipantSubscription.get().getId()
        );
    }

    @Test
    public void should_save_and_find_subscriptions_by_participant_id_and_set_of_symbols() {
        List<ParticipantSubscriptionEntity> expectedParticipantSubscriptions = randomParticipantSubscriptions(3);

        List<ParticipantSubscriptionEntity> actualParticipantSubscriptions = participantsRepository.findSubscriptions(
                expectedParticipantSubscriptions.get(0).getParticipantId(),
                expectedParticipantSubscriptions.stream().map(ParticipantSubscriptionEntity::getSymbol).collect(Collectors.toList())
        );

        Assertions.assertEquals(
                expectedParticipantSubscriptions.stream()
                        .map(participantSubscription -> participantSubscription.getId())
                        .sorted()
                        .collect(Collectors.toList()),
                actualParticipantSubscriptions.stream()
                        .map(participantSubscription -> participantSubscription.getId())
                        .sorted()
                        .collect(Collectors.toList())
        );
    }
}
