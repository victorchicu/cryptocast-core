package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderType;

import java.math.BigDecimal;

public class LimitOrderDto extends OrderDto {
    private final BigDecimal orderQty;
    private final BigDecimal orderPrice;

    @JsonCreator
    public LimitOrderDto(OrderType type, OrderSide side, BigDecimal orderQty, BigDecimal orderPrice) {
        super(type, side);
        this.orderQty = orderQty;
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
}
