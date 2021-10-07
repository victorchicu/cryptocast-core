package com.crypto.core.aggregators.services.impl;

import com.crypto.core.aggregators.services.SymbolAggregatorService;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.aggregators.dto.SymbolDto;
import com.crypto.core.watchlist.services.WatchlistService;
import com.crypto.core.yahoo.dto.SymbolsDto;
import com.crypto.core.yahoo.feigns.BinanceClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SymbolAggregatorServiceImpl implements SymbolAggregatorService {
    private final BinanceClient binanceClient;
    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final BinanceProperties binanceProperties;

    public SymbolAggregatorServiceImpl(
            BinanceClient binanceClient,
            BinanceService binanceService,
            WatchlistService watchlistService,
            BinanceProperties binanceProperties
    ) {
        this.binanceClient = binanceClient;
        this.binanceService = binanceService;
        this.watchlistService = watchlistService;
        this.binanceProperties = binanceProperties;
    }

    @Override
    public Page<SymbolDto> listSupportedSymbols(Principal principal, Pageable pageable) {
        SymbolsDto symbolsDto = binanceClient.fetchSymbols();
        List<SymbolDto> symbols = symbolsDto.getData().stream()
                .map(this::toCoinDto)
                .filter(skipUnsupportedCoins())
                .sorted(Comparator.comparing(SymbolDto::getPrice).reversed())
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(symbols, pageable, () -> binanceProperties.getSymbolTether().size());
    }

    private SymbolDto toCoinDto(SymbolsDto.Data data) {
        BinanceProperties.Coin coin = binanceProperties.getSymbolTether().get(data.getName());
        if (coin != null) {
            return new SymbolDto(data.getName(), coin.getAlias(), coin.getIcon(), false, data.getMarketCap());
        }
        return null;
    }

    private Predicate<SymbolDto> skipUnsupportedCoins() {
        return (SymbolDto symbolDto) -> symbolDto != null && symbolDto.getPrice() != null;
    }
}
