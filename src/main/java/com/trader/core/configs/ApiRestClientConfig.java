package com.trader.core.configs;

import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.exceptions.ApiClientException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.UserService;
import com.trader.core.services.ExchangeStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class ApiRestClientConfig {
    private final UserService userService;
    private final ExchangeStrategy exchangeStrategy;

    public ApiRestClientConfig(
            UserService userService,
            ExchangeStrategy exchangeStrategy
    ) {
        this.userService = userService;
        this.exchangeStrategy = exchangeStrategy;
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ApiRestClient apiRestClient() {
        return userService.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                            user.getExchangeProvider()
                    );
                    return exchangeService.newApiRestClient(user);
                })
                .orElseThrow(() -> new ApiClientException("Could not create rest client"));
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ApiWebSocketClient apiWebSocketClient() {
        return userService.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(
                            user.getExchangeProvider()
                    );
                    return exchangeService.newApiWebSocketClient(user);
                })
                .orElseThrow(() -> new ApiClientException("Could not create web socket client"));
    }
}
