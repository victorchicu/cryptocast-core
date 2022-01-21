package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderType;

import java.math.BigDecimal;

public class MarketOrderDto extends OrderDto {
    private final BigDecimal qty;

    @JsonCreator
    public MarketOrderDto(OrderType type, OrderSide side, BigDecimal qty) {
        super(type, side);
        this.qty = qty;
    }

    public BigDecimal getQty() {
        return qty;
    }
}
