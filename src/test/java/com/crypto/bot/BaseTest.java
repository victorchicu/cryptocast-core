package com.crypto.bot;

import com.crypto.bot.binance.api.domain.event.SymbolMiniTickerEvent;
import com.crypto.bot.binance.configs.BinanceProperties;
import com.crypto.bot.freemarker.services.FreeMarkerTemplateService;
import com.crypto.bot.picocli.services.PicoCliService;
import com.crypto.bot.repository.SubscriptionsRepository;
import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.crypto.bot.binance.services.BinanceService;
import com.crypto.bot.services.SubscriptionsService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;
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
    protected ConversionService conversionService;
    @Autowired
    protected BinanceProperties binanceProperties;
    @Autowired
    protected TelegramBotService telegramBotService;
    @Autowired
    protected SubscriptionsService subscriptionsService;
    @Autowired
    protected SubscriptionsRepository participantsRepository;
    @Autowired
    protected FreeMarkerTemplateService freeMarkerTemplateService;

    protected SymbolMiniTickerEvent randomSymbolMiniTickerEvent() {
        SymbolMiniTickerEvent symbolMiniTickerEvent = new SymbolMiniTickerEvent();
        Set<String> symbols = binanceProperties.getMappings().keySet();
        symbolMiniTickerEvent.setSymbol(faker.options().option(symbols.toArray(new String[0])));
        symbolMiniTickerEvent.setLow(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setHigh(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setOpen(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setClose(new BigDecimal(Math.random()));
        return symbolMiniTickerEvent;
    }

    protected SubscriptionEntity randomParticipantSubscription() {
        return new SubscriptionEntity()
                .setChatId(faker.number().randomNumber())
                .setSymbolName(faker.currency().name())
                .setMessageId(faker.number().randomDigit())
                .setParticipantId(faker.number().randomDigit());
    }

    protected List<SubscriptionEntity> randomParticipantSubscriptions(int n) {
        int participantId = faker.number().randomDigit();
        long chatId = faker.number().randomNumber();
        List<SubscriptionEntity> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            SubscriptionEntity participantSubscription = new SubscriptionEntity()
                    .setChatId(chatId)
                    .setSymbolName(faker.currency().name())
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
