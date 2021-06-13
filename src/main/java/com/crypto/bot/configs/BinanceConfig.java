package com.crypto.bot.configs;

import com.crypto.bot.binance.RequestOptions;
import com.crypto.bot.binance.SubscriptionClient;
import com.crypto.bot.binance.SubscriptionOptions;
import com.crypto.bot.binance.SyncRequestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfig {
    @Bean
    public SyncRequestClient syncRequestClient(BinanceProperties binanceProperties) {
        return SyncRequestClient.create(
                binanceProperties.getApiKey(),
                binanceProperties.getSecretKey(),
                new RequestOptions()
        );
    }

    @Bean
    public SubscriptionClient subscriptionClient(BinanceProperties binanceProperties) {
        return SubscriptionClient.create(
                createSubscriptionOptions(binanceProperties)
        );
    }


    private SubscriptionOptions createSubscriptionOptions(BinanceProperties binanceProperties) {
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(binanceProperties.getUrl());
        subscriptionOptions.setAutoReconnect(true);
        subscriptionOptions.setReceiveLimitMs(60000);
        return subscriptionOptions;
    }
}
