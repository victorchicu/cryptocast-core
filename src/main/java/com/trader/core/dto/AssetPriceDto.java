package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetPriceDto {
    private final String symbol;
    private final BigDecimal price;

    @JsonCreator
    public AssetPriceDto(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
