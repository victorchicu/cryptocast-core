package com.crypto.core.exchanges.binance.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class SymbolPriceDto {
    private final String symbolName;
    private final BigDecimal price;

    @JsonCreator
    public SymbolPriceDto(String symbolName, BigDecimal price) {
        this.symbolName = symbolName;
        this.price = price;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
