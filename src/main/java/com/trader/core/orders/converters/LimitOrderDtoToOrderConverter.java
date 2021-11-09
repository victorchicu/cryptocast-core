package com.trader.core.orders.converters;

import com.trader.core.orders.dto.OrderDto;
import com.trader.core.orders.dto.orders.LimitOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LimitOrderDtoToOrderConverter implements Converter<LimitOrderDto, OrderDto> {
    @Override
    public OrderDto convert(LimitOrderDto source) {
        return null;
    }
}
