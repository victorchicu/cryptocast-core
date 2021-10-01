package com.crypto.core.yahoo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.util.List;

public class SymbolsDto {
    private final List<Data> data;

    @JsonCreator
    public SymbolsDto(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        private final String name;
        private final BigDecimal marketCap;

        @JsonCreator
        public Data(String name, BigDecimal marketCap, BigDecimal price) {
            this.name = name;
            this.marketCap = marketCap;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getMarketCap() {
            return marketCap;
        }
    }
}
