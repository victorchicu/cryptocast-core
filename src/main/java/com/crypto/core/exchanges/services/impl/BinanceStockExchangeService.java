package com.crypto.core.exchanges.services.impl;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.exceptions.SymbolNotFoundException;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.exchanges.dto.SymbolDto;
import com.crypto.core.exchanges.services.StockExchangeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BinanceStockExchangeService implements StockExchangeService {
    private final BinanceService binanceService;
    private final BinanceProperties binanceProperties;
    private final ConversionService conversionService;

    public BinanceStockExchangeService(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            ConversionService conversionService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.conversionService = conversionService;
    }

    @Override
    public Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        int skipElements = pageable.getPageNumber() * pageable.getPageSize();
        int elementsPerPage = pageable.getPageSize();

        List<SymbolPrice> symbolPrices = binanceService.getAllSymbolPrices();

        List<SymbolDto> symbols = symbolPrices.stream()
                .sorted(Comparator.comparing(SymbolPrice::getPrice).reversed())
                .filter(skipBlacklist())
                .skip(skipElements)
                .limit(elementsPerPage)
                .map(symbolPrice -> {
                    BinanceProperties.Symbol symbol = binanceProperties.getSymbols().get(symbolPrice.getSymbol());
                    symbol = Optional.ofNullable(symbol).orElseThrow(() -> new SymbolNotFoundException(symbolPrice.getSymbol()));
                    return new SymbolDto(symbol.getIdx(), symbol.getName(), symbol.getAlias(), symbolPrice.getPrice());
                })
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(symbols, pageable, () -> binanceProperties.getSymbols().size());
    }

    private Predicate<SymbolPrice> skipBlacklist() {
        return (SymbolPrice symbolPrice) -> !binanceProperties.getBlacklist().contains(symbolPrice.getSymbol());
    }
}
