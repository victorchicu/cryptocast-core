package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class OhlcDto {
    private final Long time;
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal close;

    @JsonCreator
    public OhlcDto(Long time, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Long getTime() {
        return time;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getClose() {
        return close;
    }
}
