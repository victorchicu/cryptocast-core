package com.crypto.core.binance.orders.domain;

import com.crypto.core.binance.client.domain.OrderSide;
import com.crypto.core.binance.client.domain.OrderType;

public abstract class Order {
    private OrderType type;
    private OrderSide side;

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }
}
