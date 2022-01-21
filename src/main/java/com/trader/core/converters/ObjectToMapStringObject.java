package com.trader.core.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ObjectToMapStringObject implements Converter<Object, Map<String, Object>> {
    private final ObjectMapper objectMapper;

    public ObjectToMapStringObject(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> convert(Object source) {
        return objectMapper.convertValue(source, Map.class);
    }
}
