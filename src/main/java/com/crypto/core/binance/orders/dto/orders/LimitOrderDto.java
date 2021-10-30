package com.crypto.core.binance.orders.dto.orders;

import com.crypto.core.binance.client.domain.OrderSide;
import com.crypto.core.binance.orders.dto.OrderDto;
import com.crypto.core.binance.orders.enums.OrderType;

public class LimitOrderDto extends OrderDto {
    public LimitOrderDto(OrderType type, OrderSide side) {
        super(type, side);
    }
}
