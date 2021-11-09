package com.trader.core.orders.dto.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.client.domain.OrderSide;
import com.trader.core.binance.client.domain.OrderType;
import com.trader.core.orders.dto.OrderDto;

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
