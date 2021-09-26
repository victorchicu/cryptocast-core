package com.crypto.core.exchanges.services.impl;

import com.crypto.core.exchanges.binance.configs.BinanceProperties;
import com.crypto.core.exchanges.dto.SymbolDto;
import com.crypto.core.exchanges.services.ExchangerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinanceExchangerService implements ExchangerService {
    private final BinanceProperties binanceProperties;

    public BinanceExchangerService(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public Page<SymbolDto> listSymbols(Principal principal, Pageable pageable) {
        List<SymbolDto> symbols = binanceProperties.getSymbols().keySet().stream()
                .map(SymbolDto::new)
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(symbols, pageable, () -> binanceProperties.getSymbols().size());
    }
}
