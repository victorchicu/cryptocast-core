package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.picocli.services.BinanceService;
import com.cryptostrophe.bot.picocli.services.ParticipantSubscriptionsService;
import com.cryptostrophe.bot.picocli.services.PicoCliService;
import com.cryptostrophe.bot.picocli.services.SymbolTickerEventService;
import com.cryptostrophe.bot.repository.ParticipantsRepository;
import com.cryptostrophe.bot.repository.SymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import com.cryptostrophe.bot.freemarker.services.FreeMarkerTemplateService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class BaseTest {
    protected static Faker faker = new Faker();

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
    protected SymbolTickerEventService symbolTickerEventService;
    @Autowired
    protected SymbolTickerEventRepository symbolTickerEventRepository;
    @Autowired
    protected ParticipantSubscriptionsService participantSubscriptionsService;

    protected SymbolTickerEventEntity randomSymbolTickerEvent() {
        return new SymbolTickerEventEntity()
                .setEventTime(Instant.now().toEpochMilli())
                .setSymbol(faker.currency().name())
                .setParticipantId(faker.number().randomDigit());
    }

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
}
