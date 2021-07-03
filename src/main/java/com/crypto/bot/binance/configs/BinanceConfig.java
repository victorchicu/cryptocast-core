package com.crypto.bot.binance.configs;

import com.crypto.bot.binance.client.RequestOptions;
import com.crypto.bot.binance.client.SubscriptionClient;
import com.crypto.bot.binance.client.SubscriptionOptions;
import com.crypto.bot.binance.client.SyncRequestClient;
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
