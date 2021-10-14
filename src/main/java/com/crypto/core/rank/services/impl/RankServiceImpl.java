package com.crypto.core.rank.services.impl;

import com.crypto.core.rank.services.RankService;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.rank.dto.SymbolDto;
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
public class RankServiceImpl implements RankService {
    private final BinanceClient binanceClient;
    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final BinanceProperties binanceProperties;

    public RankServiceImpl(
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
    public Page<SymbolDto> rankCoins(Principal principal, Pageable pageable) {
        SymbolsDto symbolsDto = binanceClient.fetchSymbols();
        List<SymbolDto> symbols = symbolsDto.getData().stream()
                .map(this::toSymbolDto)
                .filter(skipUnsupportedSymbols())
                .sorted(Comparator.comparing(SymbolDto::getPrice).reversed())
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(symbols, pageable, () -> binanceProperties.getAssets().size());
    }

    private SymbolDto toSymbolDto(SymbolsDto.Data data) {
        BinanceProperties.Asset asset = binanceProperties.getAssets().get(data.getName());
        if (asset != null) {
            return new SymbolDto(data.getName(), asset.getAlias(), asset.getIcon(), false, data.getMarketCap());
        }
        return null;
    }

    private Predicate<SymbolDto> skipUnsupportedSymbols() {
        return (SymbolDto symbolDto) -> symbolDto != null && symbolDto.getPrice() != null;
    }
}
