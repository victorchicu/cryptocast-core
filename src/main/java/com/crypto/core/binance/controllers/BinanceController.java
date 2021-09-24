package com.crypto.core.binance.controllers;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.dto.SymbolPriceDto;
import com.crypto.core.binance.services.BinanceService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/binance/{symbolName}")
public class BinanceController {
    private final BinanceService binanceService;
    private final ConversionService conversionService;

    public BinanceController(BinanceService binanceService, ConversionService conversionService) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
    }

    @RequestMapping("/price")
    public SymbolPriceDto getSymbolPrice(@PathVariable String symbolName) {
        SymbolPrice symbolPrice = binanceService.getSymbolPrice(symbolName);
        return toSymbolPriceDto(symbolPrice);
    }

    private SymbolPriceDto toSymbolPriceDto(SymbolPrice symbolPrice) {
        return conversionService.convert(symbolPrice, SymbolPriceDto.class);
    }
}
