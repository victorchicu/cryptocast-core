package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LimitOrderDto.class, name = "LIMIT"),
        @JsonSubTypes.Type(value = MarketOrderDto.class, name = "MARKET")
})
public abstract class OrderDto {
    private final OrderType orderType;
    private final OrderSide orderSide;

    public OrderDto(OrderType orderType, OrderSide orderSide) {
        this.orderType = orderType;
        this.orderSide = orderSide;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderSide getOrderSide() {
        return orderSide;
    }
}
