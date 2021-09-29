package com.crypto.core.markets.controllers;

import com.crypto.core.markets.dto.CoinDto;
import com.crypto.core.markets.services.CoinMarketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/markets")
public class CoinMarketController {
    private final CoinMarketService coinMarketService;

    public CoinMarketController(CoinMarketService coinMarketService) {
        this.coinMarketService = coinMarketService;
    }

    @GetMapping
    public Page<CoinDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        return coinMarketService.listSupportedCoins(principal, pageable);
    }
}
