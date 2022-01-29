package com.trader.core;

import com.trader.core.configs.BinanceProperties;
import com.trader.core.services.AssetService;
import com.trader.core.repository.SubscriptionRepository;
import com.trader.core.services.SubscriptionService;
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
    protected AssetService assetService;
    @Autowired
    protected ConversionService conversionService;
    @Autowired
    protected BinanceProperties binanceProperties;
    @Autowired
    protected SubscriptionService subscriptionService;
    @Autowired
    protected SubscriptionRepository participantsRepository;

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.mongodb.uri=" + mongoDBContainer.getReplicaSetUrl())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}