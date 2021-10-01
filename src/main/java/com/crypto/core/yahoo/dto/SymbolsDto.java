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
        private final BigDecimal price;
        private final BigDecimal circulatingSupply;

        @JsonCreator
        public Data(String name, BigDecimal circulatingSupply, BigDecimal price) {
            this.name = name;
            this.circulatingSupply = circulatingSupply;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public BigDecimal getCirculatingSupply() {
            return circulatingSupply;
        }
    }
}
