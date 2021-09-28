package com.crypto.core.exchanges.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class SymbolDto {
    private final Integer idx;
    private final String name;
    private final String alias;
    private final BigDecimal price;

    @JsonCreator
    public SymbolDto(Integer idx, String name, String alias, BigDecimal price) {
        this.idx = idx;
        this.name = name;
        this.alias = alias;
        this.price = price;
    }

    public Integer getIdx() {
        return idx;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
