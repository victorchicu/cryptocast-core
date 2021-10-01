package com.crypto.core.aggregators.controllers;

import com.crypto.core.aggregators.dto.CoinDto;
import com.crypto.core.aggregators.services.CoinAggregatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/aggregator")
public class CoinAggregatorController {
    private final CoinAggregatorService coinAggregatorService;

    public CoinAggregatorController(CoinAggregatorService coinAggregatorService) {
        this.coinAggregatorService = coinAggregatorService;
    }

    @GetMapping
    public Page<CoinDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        return coinAggregatorService.listSupportedCoins(principal, pageable);
    }
}
