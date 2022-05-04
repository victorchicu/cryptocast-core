package com.coinbank.core.converters;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.dto.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToMarketOrderDtoConverter implements Converter<Order, MarketOrderDto> {
    @Override
    public MarketOrderDto convert(Order source) {
        return null;
    }
}
