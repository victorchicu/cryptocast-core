package com.crypto.core.markets.services.impl;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.exceptions.SymbolNotFoundException;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.handlers.GlobalExceptionHandler;
import com.crypto.core.markets.dto.CoinDto;
import com.crypto.core.markets.services.CoinMarketService;
import com.crypto.core.watchlist.services.WatchlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BinanceCoinMarketService implements CoinMarketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BinanceCoinMarketService.class);

    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final BinanceProperties binanceProperties;

    public BinanceCoinMarketService(
            BinanceService binanceService,
            WatchlistService watchlistService,
            BinanceProperties binanceProperties
    ) {
        this.binanceService = binanceService;
        this.watchlistService = watchlistService;
        this.binanceProperties = binanceProperties;
    }

    @Override
    public Page<CoinDto> listSupportedCoins(Principal principal, Pageable pageable) {
        int skipElements = pageable.getPageNumber() * pageable.getPageSize();
        int elementsPerPage = pageable.getPageSize();

        Map<String, BigDecimal> symbolPrices = binanceService.getAllSymbolPrices().stream().collect(Collectors.toMap(SymbolPrice::getSymbol, SymbolPrice::getPrice));

        //TODO: Find missing symbols

        List<CoinDto> coins = binanceProperties.getCoins().entrySet().stream()
                .map(entry -> {
                    BigDecimal price = symbolPrices.getOrDefault(entry.getKey(), BigDecimal.ZERO);
                    return new CoinDto(entry.getValue().getName(), entry.getValue().getAlias(), entry.getValue().getIcon(), false, price);
                })
                .sorted(Comparator.comparing(CoinDto::getPrice).reversed())
                .filter(skipBlacklist())
                .skip(skipElements)
                .limit(elementsPerPage)
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(coins, pageable, () -> binanceProperties.getCoins().size());
    }

    private Predicate<CoinDto> skipBlacklist() {
        return (CoinDto coin) -> !binanceProperties.getBlacklist().contains(coin.getName());
    }
}
