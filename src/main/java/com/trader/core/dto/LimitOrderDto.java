package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderType;
import com.trader.core.dto.OrderDto;

import java.math.BigDecimal;

public class LimitOrderDto extends OrderDto {
    private final BigDecimal qty;
    private final BigDecimal price;

    @JsonCreator
    public LimitOrderDto(OrderType type, OrderSide side, BigDecimal qty, BigDecimal price) {
        super(type, side);
        this.qty = qty;
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
