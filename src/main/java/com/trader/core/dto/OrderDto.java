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
    private final OrderType type;
    private final OrderSide side;

    public OrderDto(OrderType type, OrderSide side) {
        this.type = type;
        this.side = side;
    }

    public OrderType getType() {
        return type;
    }

    public OrderSide getSide() {
        return side;
    }
}
