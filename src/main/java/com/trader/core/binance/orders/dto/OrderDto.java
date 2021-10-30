package com.trader.core.binance.orders.dto;

import com.trader.core.binance.client.domain.OrderSide;
import com.trader.core.binance.orders.dto.orders.LimitOrderDto;
import com.trader.core.binance.orders.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LimitOrderDto.class, name = "LIMIT"),
})
public abstract class OrderDto {
    private final OrderType type;
    private final OrderSide side;

    @JsonCreator
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
