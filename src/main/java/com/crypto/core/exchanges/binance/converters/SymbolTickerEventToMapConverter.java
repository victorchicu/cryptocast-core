package com.crypto.core.exchanges.binance.converters;

import com.crypto.core.exchanges.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.exchanges.binance.dto.SymbolTickerEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SymbolTickerEventToMapConverter implements Converter<SymbolTickerEvent, SymbolTickerEventDto> {
    @Override
    public SymbolTickerEventDto convert(SymbolTickerEvent source) {
        return new SymbolTickerEventDto();
    }
}
