package com.crypto.core.exchanges.binance.converters;

import com.crypto.core.exchanges.binance.client.domain.market.SymbolPrice;
import com.crypto.core.exchanges.binance.dto.SymbolPriceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SymbolPriceToDtoConverter implements Converter<SymbolPrice, SymbolPriceDto> {
    @Override
    public SymbolPriceDto convert(SymbolPrice source) {
        return new SymbolPriceDto(source.getSymbol(), source.getPrice());
    }
}
