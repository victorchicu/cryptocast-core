package com.trader.core.converters;

import com.binance.api.client.domain.account.Order;
import com.trader.core.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToDtoConverter implements Converter<Order, OrderDto> {
    @Override
    public OrderDto convert(Order source) {
        return new OrderDto(
                source.getTime(),
                source.getOrderId(),
                null,
                source.getUpdateTime(),
                source.getPrice(),
                source.getSymbol(),
                source.getOrigQty(),
                source.getStopPrice(),
                source.getIcebergQty(),
                source.getExecutedQty(),
                source.getClientOrderId(),
                source.getOrigQuoteOrderQty(),
                source.getCummulativeQuoteQty(),
                source.isWorking(),

                source.getType(),
                source.getSide(),
                source.getStatus(),
                source.getTimeInForce()
        );
    }
}
