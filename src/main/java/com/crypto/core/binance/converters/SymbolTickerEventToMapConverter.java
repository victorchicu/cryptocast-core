package com.crypto.core.binance.converters;

import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SymbolTickerEventToMapConverter implements Converter<SymbolTickerEvent, Map<String, Object>> {
    private final ObjectMapper objectMapper;

    public SymbolTickerEventToMapConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> convert(SymbolTickerEvent source) {
        return objectMapper.convertValue(source, Map.class);
    }
}
