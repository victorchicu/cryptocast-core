package com.trader.core.configs;

import com.trader.core.binance.BinanceApiClientFactory;
import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.BinanceApiWebSocketClient;
import com.trader.core.binance.exception.BinanceApiException;
import com.trader.core.services.UserService;
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
