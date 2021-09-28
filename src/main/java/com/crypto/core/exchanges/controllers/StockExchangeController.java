package com.crypto.core.exchanges.controllers;

import com.crypto.core.exchanges.dto.SymbolDto;
import com.crypto.core.exchanges.services.StockExchangeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/exchanges")
public class StockExchangeController {
    private final StockExchangeService stockExchangeService;

    public StockExchangeController(StockExchangeService stockExchangeService) {
        this.stockExchangeService = stockExchangeService;
    }

    @GetMapping
    public Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        return stockExchangeService.listSupportedSymbols(principal, pageable);
    }
}
