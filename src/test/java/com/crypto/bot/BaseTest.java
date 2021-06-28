package com.crypto.bot;

import com.crypto.bot.services.BinanceService;
import com.crypto.bot.repository.ParticipantsRepository;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.crypto.bot.binance.model.event.SymbolMiniTickerEvent;
import com.crypto.bot.configs.BinanceProperties;
import com.crypto.bot.services.ParticipantSubscriptionsService;
import com.crypto.bot.picocli.services.PicoCliService;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import com.crypto.bot.freemarker.services.FreeMarkerTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(initializers = BaseTest.Initializer.class)
public class BaseTest {
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    protected static final Faker faker = new Faker();

    static {
        mongoDBContainer.start();
    }

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PicoCliService picoCliService;
    @Autowired
    protected BinanceService binanceService;
    @Autowired
    protected BinanceProperties binanceProperties;
    @Autowired
    protected TelegramBotService telegramBotService;
    @Autowired
    protected ParticipantsRepository participantsRepository;
    @Autowired
    protected FreeMarkerTemplateService freeMarkerTemplateService;
    @Autowired
    protected ParticipantSubscriptionsService participantSubscriptionsService;

    protected SymbolMiniTickerEvent randomSymbolMiniTickerEvent() {
        SymbolMiniTickerEvent symbolMiniTickerEvent = new SymbolMiniTickerEvent();
        Set<String> symbols = binanceProperties.getCryptocurrency().keySet();
        symbolMiniTickerEvent.setSymbol(faker.options().option(symbols.toArray(new String[0])));
        symbolMiniTickerEvent.setLow(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setHigh(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setOpen(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setClose(new BigDecimal(Math.random()));
        return symbolMiniTickerEvent;
    }

    protected ParticipantSubscriptionEntity randomParticipantSubscription() {
        return new ParticipantSubscriptionEntity()
                .setChatId(faker.number().randomNumber())
                .setSymbol(faker.currency().name())
                .setMessageId(faker.number().randomDigit())
                .setParticipantId(faker.number().randomDigit());
    }

    protected List<ParticipantSubscriptionEntity> randomParticipantSubscriptions(int n) {
        int participantId = faker.number().randomDigit();
        long chatId = faker.number().randomNumber();
        List<ParticipantSubscriptionEntity> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            ParticipantSubscriptionEntity participantSubscription = new ParticipantSubscriptionEntity()
                    .setChatId(chatId)
                    .setSymbol(faker.currency().name())
                    .setMessageId(faker.number().randomDigit())
                    .setParticipantId(participantId);
            list.add(participantsRepository.save(participantSubscription));
        }
        return list;
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.mongodb.uri=" + mongoDBContainer.getReplicaSetUrl())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
