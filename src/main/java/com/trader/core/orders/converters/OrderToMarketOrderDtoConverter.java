package com.trader.core.orders.converters;

import com.trader.core.binance.client.domain.account.Order;
import com.trader.core.orders.dto.orders.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToMarketOrderDtoConverter implements Converter<Order, MarketOrderDto> {
    @Override
    public MarketOrderDto convert(Order source) {
        return null;
    }
}
