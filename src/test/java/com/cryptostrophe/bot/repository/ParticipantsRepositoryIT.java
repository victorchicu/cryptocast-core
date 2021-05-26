package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.BaseTest;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.github.javafaker.Faker;
import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.Collectors;

public class ParticipantsRepositoryIT extends BaseTest {
    @Test
    public void should_save_and_find_subscription_by_participant_id_and_symbol() {
        ParticipantSubscription expectedParticipantSubscription = participantsRepository.save(
                randomParticipantSubscription()
        );

        Optional<ParticipantSubscription> actualParticipantSubscription = participantsRepository.findSubscription(
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
        List<ParticipantSubscription> expectedParticipantSubscriptions = randomParticipantSubscriptions(3);

        List<ParticipantSubscription> actualParticipantSubscriptions = participantsRepository.findSubscriptions(
                expectedParticipantSubscriptions.get(0).getParticipantId(),
                expectedParticipantSubscriptions.stream().map(ParticipantSubscription::getSymbol).collect(Collectors.toList())
        );

        Assertions.assertEquals(
                expectedParticipantSubscriptions.stream()
                        .map(participantSubscription -> participantSubscription.getId())
                        .collect(Collectors.toList()),
                actualParticipantSubscriptions.stream()
                        .map(participantSubscription -> participantSubscription.getId())
                        .collect(Collectors.toList())
        );
    }
}