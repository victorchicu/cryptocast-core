package com.coinbank.core.converters;

import com.coinbank.core.domain.TestOrder;
import com.coinbank.core.dto.OrderRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTestOrderConverter implements Converter<OrderRequestDto, TestOrder> {
    @Override
    public TestOrder convert(OrderRequestDto source) {
        return new TestOrder()
                .setSide(source.getSide())
                .setType(source.getType())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity());
    }
}
