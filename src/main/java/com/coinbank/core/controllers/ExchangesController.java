package com.coinbank.core.controllers;

import com.coinbank.core.domain.ApiKey;
import com.coinbank.core.domain.User;
import com.coinbank.core.dto.ApiKeyDto;
import com.coinbank.core.exceptions.UserNotFoundException;
import com.coinbank.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangesController {
    private final UserService userService;
    private final ConversionService conversionService;

    public ExchangesController(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public void addApiKey(Principal principal, @RequestBody ApiKeyDto apiKeyDto) {
        userService.findById(principal.getName())
                .map((User user) -> {
                    ApiKey apiKey = this.toApiKey(apiKeyDto);
                    user.addApiKey(apiKey);
                    return user;
                })
                .map(userService::save)
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping("/{name}")
    public void deleteApiKey(Principal principal, @PathVariable String name) {
        userService.findById(principal.getName())
                .map((User user) -> {
                    user.deleteApiKey(name);
                    return user;
                })
                .map(userService::save)
                .orElseThrow(UserNotFoundException::new);
    }


    private ApiKey toApiKey(ApiKeyDto apiKeyDto) {
        return conversionService.convert(apiKeyDto, ApiKey.class);
    }
}
