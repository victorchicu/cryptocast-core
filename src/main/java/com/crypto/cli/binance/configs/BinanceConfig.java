package com.crypto.cli.binance.configs;

import com.crypto.cli.binance.client.SubscriptionClient;
import com.crypto.cli.binance.client.SubscriptionOptions;
import com.crypto.cli.binance.client.SyncRequestClient;
import com.crypto.cli.stubs.SubscriptionClientStub;
import com.crypto.cli.stubs.SyncRequestClientStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfig {
    @Bean
    public SyncRequestClient syncRequestClient(BinanceProperties binanceProperties) {
        return new SyncRequestClientStub();
//        return SyncRequestClient.create(binanceProperties.getApiKey(), binanceProperties.getSecretKey(), new RequestOptions());
    }

    @Bean
    public SubscriptionClient subscriptionClient(BinanceProperties binanceProperties) {
        return new SubscriptionClientStub();
//        return SubscriptionClient.create(createSubscriptionOptions(binanceProperties));
    }


    private SubscriptionOptions createSubscriptionOptions(BinanceProperties binanceProperties) {
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(binanceProperties.getUrl());
        subscriptionOptions.setAutoReconnect(true);
        subscriptionOptions.setReceiveLimitMs(60000);
        return subscriptionOptions;
    }
}
