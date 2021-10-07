package com.crypto.core.aggregators.controllers;

import com.crypto.core.aggregators.dto.SymbolDto;
import com.crypto.core.aggregators.services.SymbolAggregatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/aggregator")
public class SymbolAggregatorController {
    private final SymbolAggregatorService symbolAggregatorService;

    public SymbolAggregatorController(SymbolAggregatorService symbolAggregatorService) {
        this.symbolAggregatorService = symbolAggregatorService;
    }

    @GetMapping
    public Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        return symbolAggregatorService.listSupportedSymbols(principal, pageable);
    }
}
