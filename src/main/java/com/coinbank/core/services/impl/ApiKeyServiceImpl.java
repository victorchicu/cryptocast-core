package com.coinbank.core.services.impl;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.domain.User;
import com.coinbank.core.services.ApiKeyService;
import com.coinbank.core.services.ExchangeProvider;
import com.coinbank.core.services.UserService;
import com.coinbank.core.domain.exceptions.UserNotFoundException;
import com.coinbank.core.services.ExchangeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    private final UserService userService;
    private final ExchangeProvider exchangeProvider;
    private final Map<String, ExchangeService> exchanges;

    private ApiKeyServiceImpl(UserService userService, ExchangeProvider exchangeProvider, @Lazy Map<String, ExchangeService> exchanges) {
        this.userService = userService;
        this.exchangeProvider = exchangeProvider;
        this.exchanges = exchanges;
    }

    @Override
    public void create(Principal principal, ApiKey apiKey) {
        userService.findById(principal.getName())
                .ifPresentOrElse(
                        user -> {
                            user.addApiKey(apiKey);
                            exchanges.put(apiKey.getLabel(), exchangeProvider.get(apiKey));
                            userService.save(user);
                        }, () -> {
                            throw new UserNotFoundException();
                        });
    }

    @Override
    public void delete(Principal principal, String label) {
        userService.findById(principal.getName())
                .ifPresentOrElse(
                        user -> {
                            user.deleteApiKey(label);
                            exchanges.remove(label);
                            userService.save(user);
                        }, () -> {
                            throw new UserNotFoundException();
                        });
    }

    @Override
    public List<ApiKey> list(Principal principal) {
        return userService.findById(principal.getName())
                .map(user -> user.getExchanges().values()).stream()
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Map<String, ExchangeService> exchanges() {
        return userService.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .map((User user) ->
                        user.getExchanges().entrySet().stream()
                                .collect(
                                        Collectors.toMap(
                                                Map.Entry::getKey,
                                                entry -> exchangeProvider.get(entry.getValue())
                                        )
                                ))
                .orElseThrow(UserNotFoundException::new);
    }
}
