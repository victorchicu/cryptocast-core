package com.crypto.core;

import com.crypto.core.binance.client.domain.event.SymbolMiniTickerEvent;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.freemarker.services.FreeMarkerTemplateService;
import com.crypto.core.watchlist.repository.WatchlistRepository;
import com.crypto.core.watchlist.services.WatchlistService;
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
import java.time.Instant;
import java.util.Set;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(initializers = TestBase.Initializer.class)
public class TestBase {
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    protected static final Faker faker = new Faker();

    static {
        mongoDBContainer.start();
    }

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected BinanceService binanceService;
    @Autowired
    protected ConversionService conversionService;
    @Autowired
    protected BinanceProperties binanceProperties;
    @Autowired
    protected WatchlistService watchlistService;
    @Autowired
    protected WatchlistRepository participantsRepository;
    @Autowired
    protected FreeMarkerTemplateService freeMarkerTemplateService;

    protected SymbolMiniTickerEvent randomSymbolMiniTickerEvent() {
        SymbolMiniTickerEvent symbolMiniTickerEvent = new SymbolMiniTickerEvent();
        Set<String> symbols = binanceProperties.getTetherToSymbol().keySet();
        symbolMiniTickerEvent.setLow(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setHigh(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setOpen(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setClose(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setSymbol(faker.options().option(symbols.toArray(new String[0])));
        symbolMiniTickerEvent.setEventTime(Instant.now().toEpochMilli());
        symbolMiniTickerEvent.setTotalTradedBaseAssetVolume(new BigDecimal(Math.random()));
        symbolMiniTickerEvent.setTotalTradedQuoteAssetVolume(new BigDecimal(Math.random()));
        return symbolMiniTickerEvent;
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.mongodb.uri=" + mongoDBContainer.getReplicaSetUrl())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
