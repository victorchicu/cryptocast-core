package com.trader.core.converters;

import com.trader.core.binance.domain.account.Order;
import com.trader.core.dto.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToMarketOrderDtoConverter implements Converter<Order, MarketOrderDto> {
    @Override
    public MarketOrderDto convert(Order source) {
        return null;
    }
}
