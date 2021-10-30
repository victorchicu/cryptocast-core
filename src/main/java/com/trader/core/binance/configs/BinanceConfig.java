package com.trader.core.binance.configs;

import com.trader.core.binance.client.BinanceApiClientFactory;
import com.trader.core.binance.client.BinanceApiRestClient;
import com.trader.core.binance.client.BinanceApiWebSocketClient;
import com.trader.core.binance.client.exception.BinanceApiException;
import com.trader.core.users.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class BinanceConfig {
    private final UserService userService;
    private final BinanceProperties binanceProperties;

    public BinanceConfig(UserService userService, BinanceProperties binanceProperties) {
        this.userService = userService;
        this.binanceProperties = binanceProperties;
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BinanceApiRestClient binanceApiRestClient() {
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(user -> {
                    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                            user.getApiKey(),
                            user.getSecretKey(),
                            binanceProperties.getUseTestnet(),
                            binanceProperties.getUseTestnetStreaming()
                    );
                    return factory.newRestClient();
                })
                .orElseThrow(() -> new BinanceApiException("Could not create Binance rest client"));
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BinanceApiWebSocketClient binanceApiWebSocketClient() {
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(user -> {
                    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                            user.getApiKey(),
                            user.getSecretKey(),
                            binanceProperties.getUseTestnet(),
                            binanceProperties.getUseTestnetStreaming()
                    );
                    return factory.newWebSocketClient();
                })
                .orElseThrow(() -> new BinanceApiException("Could not create Binance rest client"));
    }
}
