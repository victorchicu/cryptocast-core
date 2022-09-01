package ai.cryptocast.core.domain.exceptions;

import com.binance.api.client.domain.OrderType;

public class IllegalOrderTypeException extends RuntimeException {
    public IllegalOrderTypeException(OrderType orderType) {
        super("Illegal order type " + orderType);
    }
}
