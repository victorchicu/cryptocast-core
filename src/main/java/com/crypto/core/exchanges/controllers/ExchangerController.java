package com.crypto.core.exchanges.controllers;

import com.crypto.core.exchanges.dto.SymbolDto;
import com.crypto.core.exchanges.services.ExchangerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/exchange")
public class ExchangerController {
    private final ExchangerService exchangerService;

    public ExchangerController(ExchangerService exchangerService) {
        this.exchangerService = exchangerService;
    }

    @GetMapping
    public Page<SymbolDto> listSymbols(Principal principal, Pageable pageable) {
        return exchangerService.listSymbols(principal, pageable);
    }
}
