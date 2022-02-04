package com.trader.core.dto;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderStatus;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.fasterxml.jackson.annotation.JsonCreator;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = LimitOrderDto.class, name = "LIMIT"),
//        @JsonSubTypes.Type(value = MarketOrderDto.class, name = "MARKET")
//})
public class OrderDto {
    private final Long time;
    private final Long orderId;
    private final Long orderListId;
    private final Long updatedTime;
    private final String price;
    private final String symbol;
    private final String origQty;
    private final String stopPrice;
    private final String icebergQty;
    private final String executedQty;
    private final String clientOrderId;
    private final String origQuoteOrderQty;
    private final String cummulativeQuoteQty;
    private final Boolean isWorking;
    private final OrderType type;
    private final OrderSide side;
    private final OrderStatus status;
    private final TimeInForce timeInForce;

    @JsonCreator
    public OrderDto(
            Long time,
            Long orderId,
            Long orderListId,
            Long updatedTime,
            String price,
            String symbol,
            String origQty,
            String stopPrice,
            String icebergQty,
            String executedQty,
            String clientOrderId,
            String origQuoteOrderQty,
            String cummulativeQuoteQty,
            Boolean isWorking,
            OrderType type,
            OrderSide side,
            OrderStatus status,
            TimeInForce timeInForce
    ) {
        this.time = time;
        this.orderId = orderId;
        this.orderListId = orderListId;
        this.updatedTime = updatedTime;
        this.price = price;
        this.symbol = symbol;
        this.origQty = origQty;
        this.stopPrice = stopPrice;
        this.icebergQty = icebergQty;
        this.executedQty = executedQty;
        this.clientOrderId = clientOrderId;
        this.origQuoteOrderQty = origQuoteOrderQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.isWorking = isWorking;
        this.type = type;
        this.side = side;
        this.status = status;
        this.timeInForce = timeInForce;
    }

    public Long getTime() {
        return time;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderListId() {
        return orderListId;
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public String getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOrigQty() {
        return origQty;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public String getIcebergQty() {
        return icebergQty;
    }

    public String getExecutedQty() {
        return executedQty;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public String getOrigQuoteOrderQty() {
        return origQuoteOrderQty;
    }

    public String getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public OrderType getType() {
        return type;
    }

    public OrderSide getSide() {
        return side;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }
}
