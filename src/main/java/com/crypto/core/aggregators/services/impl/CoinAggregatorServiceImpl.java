package com.crypto.core.aggregators.services.impl;

import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.aggregators.dto.CoinDto;
import com.crypto.core.aggregators.services.CoinAggregatorService;
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
public class CoinAggregatorServiceImpl implements CoinAggregatorService {
    private final BinanceClient binanceClient;
    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final BinanceProperties binanceProperties;

    public CoinAggregatorServiceImpl(
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
    public Page<CoinDto> listSupportedCoins(Principal principal, Pageable pageable) {
        SymbolsDto symbolsDto = binanceClient.fetchSymbols();
        List<CoinDto> coins = symbolsDto.getData().stream()
                .map(this::toCoinDto)
                .filter(skipUnsupportedCoins())
                .sorted(Comparator.comparing(CoinDto::getPrice).reversed())
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(coins, pageable, () -> binanceProperties.getSymbolToTether().size());
    }

    private CoinDto toCoinDto(SymbolsDto.Data data) {
        BinanceProperties.Coin coin = binanceProperties.getSymbolToTether().get(data.getName());
        if (coin != null) {
            return new CoinDto(data.getName(), coin.getAlias(), coin.getIcon(), false, data.getMarketCap());
        }
        return null;
    }

    private Predicate<CoinDto> skipUnsupportedCoins() {
        return (CoinDto coinDto) -> coinDto != null && coinDto.getPrice() != null;
    }
}
