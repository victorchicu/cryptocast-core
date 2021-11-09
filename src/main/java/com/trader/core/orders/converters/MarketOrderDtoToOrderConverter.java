package com.trader.core.orders.converters;

import com.trader.core.binance.client.domain.account.Order;
import com.trader.core.orders.dto.orders.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MarketOrderDtoToOrderConverter implements Converter<MarketOrderDto, Order> {
    @Override
    public Order convert(MarketOrderDto source) {
        return null;
    }
}
