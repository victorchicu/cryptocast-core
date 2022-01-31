package com.trader.core.dto;

import com.trader.core.binance.domain.OrderSide;
import com.trader.core.binance.domain.OrderStatus;
import com.trader.core.binance.domain.OrderType;
import com.trader.core.binance.domain.TimeInForce;

import java.math.BigDecimal;

public class MarketOrderDto extends OrderDto {
    private final BigDecimal orderQty;

    public MarketOrderDto(Long time, Long orderId, Long orderListId, Long updatedTime, String price, String symbol, String origQty, String stopPrice, String icebergQty, String executedQty, String clientOrderId, String origQuoteOrderQty, String cummulativeQuoteQty, Boolean isWorking, OrderType type, OrderSide side, OrderStatus status, TimeInForce timeInForce, BigDecimal orderQty) {
        super(time, orderId, orderListId, updatedTime, price, symbol, origQty, stopPrice, icebergQty, executedQty, clientOrderId, origQuoteOrderQty, cummulativeQuoteQty, isWorking, type, side, status, timeInForce);
        this.orderQty = orderQty;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }
}
