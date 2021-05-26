package com.cryptostrophe.bot;

import com.cryptostrophe.bot.repository.ParticipantsRepository;
import com.cryptostrophe.bot.repository.SymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class BaseTest {
    protected static Faker faker = new Faker();

    @Autowired
    protected ParticipantsRepository participantsRepository;
    @Autowired
    protected SymbolTickerEventRepository symbolTickerEventRepository;

    protected SymbolTickerEvent randomSymbolTickerEvent() {
        return new SymbolTickerEvent()
                .setEventTime(Instant.now().toEpochMilli())
                .setSymbol(faker.currency().name())
                .setParticipantId(faker.number().randomDigit());
    }

    protected ParticipantSubscription randomParticipantSubscription() {
        return new ParticipantSubscription()
                .setChatId(faker.number().randomNumber())
                .setSymbol(faker.currency().name())
                .setMessageId(faker.number().randomDigit())
                .setParticipantId(faker.number().randomDigit());
    }

    protected List<ParticipantSubscription> randomParticipantSubscriptions(int n) {
        int participantId = faker.number().randomDigit();
        long chatId = faker.number().randomNumber();
        List<ParticipantSubscription> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            ParticipantSubscription participantSubscription = new ParticipantSubscription()
                    .setChatId(chatId)
                    .setSymbol(faker.currency().name())
                    .setMessageId(faker.number().randomDigit())
                    .setParticipantId(participantId);
            list.add(participantsRepository.save(participantSubscription));
        }
        return list;
    }
}
