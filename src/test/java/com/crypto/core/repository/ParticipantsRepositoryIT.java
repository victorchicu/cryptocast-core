package com.crypto.core.repository;

import com.crypto.core.BaseTest;
import com.crypto.core.repository.entity.SubscriptionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipantsRepositoryIT extends BaseTest {
    @Test
    public void should_save_and_find_subscription_by_participant_id_and_symbol() {
        SubscriptionEntity expectedParticipantSubscription = participantsRepository.save(
                randomParticipantSubscription()
        );

        Optional<SubscriptionEntity> actualParticipantSubscription = participantsRepository.findSubscription(
                expectedParticipantSubscription.getParticipantId(),
                expectedParticipantSubscription.getSymbolName()
        );

        Assertions.assertEquals(
                expectedParticipantSubscription.getId(),
                actualParticipantSubscription.get().getId()
        );
    }

    @Test
    public void should_save_and_find_subscriptions_by_participant_id_and_set_of_symbols() {
        List<SubscriptionEntity> expectedParticipantSubscriptions = randomParticipantSubscriptions(3);

        List<SubscriptionEntity> actualParticipantSubscriptions = participantsRepository.findSubscriptions(
                expectedParticipantSubscriptions.get(0).getParticipantId(),
                expectedParticipantSubscriptions.stream().map(SubscriptionEntity::getSymbolName).collect(Collectors.toList())
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
