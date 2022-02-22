package com.trader.core.exceptions;

import com.binance.api.client.domain.OrderType;

public class IllegalOrderTypeException extends RuntimeException {
    public IllegalOrderTypeException(OrderType orderType) {
        super("Unsupported order type " + orderType);
    }
}
