package com.crypto.core.yahoo.feigns;

import com.crypto.core.yahoo.dto.SymbolsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "BinanceClient", url = "https://www.binance.com/bapi/composite/v1/public/marketing/symbol/list")
public interface BinanceClient {
    @GetMapping
    SymbolsDto fetchSymbols();
}
