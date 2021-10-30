package com.trader.core.binance.orders.domain.orders;

import com.trader.core.binance.orders.domain.Order;

import java.math.BigDecimal;

public class LimitOrder extends Order {
    private BigDecimal qty;
    private BigDecimal price;

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
