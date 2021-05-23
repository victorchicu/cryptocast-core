package com.cryptostrophe.bot.configs;

import com.cryptostrophe.bot.binance.RequestOptions;
import com.cryptostrophe.bot.binance.SubscriptionClient;
import com.cryptostrophe.bot.binance.SubscriptionOptions;
import com.cryptostrophe.bot.binance.SyncRequestClient;
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
