package com.coinbank.core.converters;

import com.coinbank.core.dto.LimitOrderDto;
import com.coinbank.core.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LimitOrderDtoToOrderConverter implements Converter<LimitOrderDto, OrderDto> {
    @Override
    public OrderDto convert(LimitOrderDto source) {
        return null;
    }
}
