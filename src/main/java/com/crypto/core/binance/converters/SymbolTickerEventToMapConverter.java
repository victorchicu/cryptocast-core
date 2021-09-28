package com.crypto.core.binance.converters;

import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.binance.dto.SymbolTickerEventDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SymbolTickerEventToMapConverter implements Converter<SymbolTickerEvent, SymbolTickerEventDto> {
    @Override
    public SymbolTickerEventDto convert(SymbolTickerEvent source) {
        return new SymbolTickerEventDto();
    }
}
