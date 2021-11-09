package com.trader.core.orders.dto.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.client.domain.OrderSide;
import com.trader.core.binance.client.domain.OrderType;
import com.trader.core.orders.dto.OrderDto;

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
