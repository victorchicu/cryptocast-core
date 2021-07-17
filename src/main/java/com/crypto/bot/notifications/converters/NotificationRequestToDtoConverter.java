package com.crypto.bot.notifications.converters;

import com.crypto.bot.notifications.domain.NotificationRequest;
import com.crypto.bot.notifications.dto.NotificationRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestToDtoConverter implements Converter<NotificationRequest, NotificationRequestDto> {
    @Override
    public NotificationRequestDto convert(NotificationRequest source) {
        return new NotificationRequestDto(source.getId(), source.getType(), source.getPayload());
    }
}
