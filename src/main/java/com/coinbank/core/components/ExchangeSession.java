package com.coinbank.core.components;

import com.coinbank.core.domain.User;
import com.coinbank.core.exceptions.UserNotFoundException;
import com.coinbank.core.services.ExchangeProvider;
import com.coinbank.core.services.ExchangeService;
import com.coinbank.core.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExchangeSession {
    private final UserService userService;
    private final ExchangeProvider exchangeProvider;

    private ExchangeSession(UserService userService, ExchangeProvider exchangeProvider) {
        this.userService = userService;
        this.exchangeProvider = exchangeProvider;
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Map<String, ExchangeService> exchanges() {
        return userService.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .map((User user) ->
                        user.getApiKeys().entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                Map.Entry::getKey,
                                                entry -> exchangeProvider.get(entry.getValue())
                                        )
                                ))
                .orElseThrow(UserNotFoundException::new);
    }
}
