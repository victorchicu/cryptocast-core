package com.crypto.core.rank.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.util.Objects;

public class SymbolDto {
    private final String name;
    private final String alias;
    private final Integer icon;
    private final Boolean inWatchlist;
    private final BigDecimal price;

    @JsonCreator
    public SymbolDto(String name, String alias, Integer icon, Boolean inWatchlist, BigDecimal price) {
        this.name = name;
        this.alias = alias;
        this.icon = icon;
        this.inWatchlist = inWatchlist;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getIcon() {
        return icon;
    }

    public Boolean getInWatchlist() {
        return inWatchlist;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolDto symbolDto = (SymbolDto) o;
        return Objects.equals(name, symbolDto.name) && Objects.equals(alias, symbolDto.alias) && Objects.equals(icon, symbolDto.icon) && Objects.equals(inWatchlist, symbolDto.inWatchlist) && Objects.equals(price, symbolDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alias, icon, inWatchlist, price);
    }
}
