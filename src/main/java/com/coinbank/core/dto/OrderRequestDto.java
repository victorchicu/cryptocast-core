package com.coinbank.core.dto;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class OrderRequestDto {
    private final OrderType type;
    private final OrderSide side;
    private final BigDecimal price;
    private final BigDecimal quantity;

    @JsonCreator
    public OrderRequestDto(OrderType type, OrderSide side, BigDecimal price, BigDecimal quantity) {
        this.type = type;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderType getType() {
        return type;
    }

    public OrderSide getSide() {
        return side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
