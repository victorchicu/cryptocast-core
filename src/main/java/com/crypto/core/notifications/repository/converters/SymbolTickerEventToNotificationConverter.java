package com.crypto.core.notifications.repository.converters;

import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.notifications.enums.NotificationEvent;
import com.crypto.core.notifications.repository.entity.NotificationEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SymbolTickerEventToNotificationConverter implements Converter<SymbolTickerEvent, NotificationEntity> {
    private final ObjectMapper objectMapper;

    public SymbolTickerEventToNotificationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public NotificationEntity convert(SymbolTickerEvent source) {
        Map<String, Object> payload = objectMapper.convertValue(source, Map.class);
        return NotificationEntity.newBuilder()
                .event(NotificationEvent.SYMBOL_TICKER_EVENT)
                .payload(payload)
                .build();
    }
}
