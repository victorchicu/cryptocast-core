package com.trader.core.binance.orders.domain.orders;

import com.trader.core.binance.orders.domain.Order;

import java.math.BigDecimal;

public class MarketOrder extends Order {
    private BigDecimal qty;

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
