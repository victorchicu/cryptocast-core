package com.trader.core.binance.orders.dto.orders;

import com.trader.core.binance.client.domain.OrderSide;
import com.trader.core.binance.orders.dto.OrderDto;
import com.trader.core.binance.orders.enums.OrderType;

public class LimitOrderDto extends OrderDto {
    public LimitOrderDto(OrderType type, OrderSide side) {
        super(type, side);
    }
}
