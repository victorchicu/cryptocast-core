package com.coinbank.core.controllers;

import com.coinbank.core.dto.ExchangeDto;
import com.coinbank.core.exceptions.UserNotFoundException;
import com.coinbank.core.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
    private final UserService userService;

    public ExchangeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<ExchangeDto> listExchanges() {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addExchange(Principal principal, @RequestBody ExchangeDto exchangeDto) {
        userService.findById(principal.getName())
                .map(user -> {
                    return user;
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
