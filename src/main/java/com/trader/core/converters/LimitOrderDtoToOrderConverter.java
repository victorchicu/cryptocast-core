package com.trader.core.converters;

import com.trader.core.dto.OrderDto;
import com.trader.core.dto.LimitOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LimitOrderDtoToOrderConverter implements Converter<LimitOrderDto, OrderDto> {
    @Override
    public OrderDto convert(LimitOrderDto source) {
        return null;
    }
}
