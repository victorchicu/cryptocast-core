package com.crypto.core.markets.services.impl;

import com.crypto.core.binance.client.domain.market.SymbolPrice;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.markets.dto.CoinDto;
import com.crypto.core.markets.services.CoinMarketService;
import com.crypto.core.watchlist.services.WatchlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BinanceCoinMarketService implements CoinMarketService {
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

        Map<String, BigDecimal> symbolPrices = binanceService.getAllSymbolPrices().stream()
                .filter(skipBlacklistCoins())
                .collect(Collectors.toMap(SymbolPrice::getSymbol, SymbolPrice::getPrice));

        List<CoinDto> coins = symbolPrices.entrySet().stream()
                .map(entry -> {
                    BinanceProperties.Coin coin = binanceProperties.getCoins().get(entry.getKey());
                    return new CoinDto(coin.getName(), coin.getAlias(), coin.getIcon(), Boolean.FALSE, entry.getValue());
                })
                .sorted(Comparator.comparing(CoinDto::getPrice).reversed())
                .skip(skipElements)
                .limit(elementsPerPage)
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(coins, pageable, () -> symbolPrices.size());
    }

    private Predicate<SymbolPrice> skipBlacklistCoins() {
        return (SymbolPrice symbolPrice) -> !binanceProperties.getBlacklist().contains(symbolPrice.getSymbol());
    }
}
