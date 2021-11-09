package com.trader.core.orders.converters;

import com.trader.core.binance.client.domain.account.Order;
import com.trader.core.orders.dto.orders.LimitOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToLimitOrderDtoConverter implements Converter<Order, LimitOrderDto> {
    @Override
    public LimitOrderDto convert(Order source) {
        return null;
    }
}
