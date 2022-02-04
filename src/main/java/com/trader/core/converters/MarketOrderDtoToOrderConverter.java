package com.trader.core.converters;

import com.binance.api.client.domain.account.Order;
import com.trader.core.dto.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MarketOrderDtoToOrderConverter implements Converter<MarketOrderDto, Order> {
    @Override
    public Order convert(MarketOrderDto source) {
        return null;
    }
}
