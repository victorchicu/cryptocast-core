package com.trader.core.domain;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;

import java.math.BigDecimal;

public class TestOrder {
    private String asset;
    private OrderSide side;
    private OrderType type;
    private BigDecimal price;
    private BigDecimal quantity;

    public String getAsset() {
        return asset;
    }

    public TestOrder setAsset(String asset) {
        this.asset = asset;
        return this;
    }

    public OrderSide getSide() {
        return side;
    }

    public TestOrder setSide(OrderSide side) {
        this.side = side;
        return this;
    }

    public OrderType getType() {
        return type;
    }

    public TestOrder setType(OrderType type) {
        this.type = type;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TestOrder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public TestOrder setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
}
