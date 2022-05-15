package com.coinbank.core.web.converters;

import com.binance.api.client.domain.account.Order;
import com.coinbank.core.web.dto.MarketOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToMarketOrderDtoConverter implements Converter<Order, MarketOrderDto> {
    @Override
    public MarketOrderDto convert(Order source) {
        return null;
    }
}
