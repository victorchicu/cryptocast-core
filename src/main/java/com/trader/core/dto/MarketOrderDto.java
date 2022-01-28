package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderType;

import java.math.BigDecimal;

public class MarketOrderDto extends OrderDto {
    private final BigDecimal orderQty;

    @JsonCreator
    public MarketOrderDto(OrderType orderType, OrderSide orderSide, BigDecimal orderQty) {
        super(orderType, orderSide);
        this.orderQty = orderQty;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }
}
