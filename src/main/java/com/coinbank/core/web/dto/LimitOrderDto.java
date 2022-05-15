package com.coinbank.core.web.dto;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderStatus;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;

import java.math.BigDecimal;

public class LimitOrderDto extends OrderDto {
    private final BigDecimal orderQty;
    private final BigDecimal orderPrice;

    public LimitOrderDto(Long time, Long orderId, Long orderListId, Long updatedTime, String price, String symbol, String origQty, String stopPrice, String icebergQty, String executedQty, String clientOrderId, String origQuoteOrderQty, String cummulativeQuoteQty, Boolean isWorking, OrderType type, OrderSide side, OrderStatus status, TimeInForce timeInForce, BigDecimal orderQty, BigDecimal orderPrice) {
        super(time, orderId, orderListId, updatedTime, price, symbol, origQty, stopPrice, icebergQty, executedQty, clientOrderId, origQuoteOrderQty, cummulativeQuoteQty, isWorking, type, side, status, timeInForce);
        this.orderQty = orderQty;
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
}
