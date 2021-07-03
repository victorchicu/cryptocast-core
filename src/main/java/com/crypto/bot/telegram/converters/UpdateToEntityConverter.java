package com.crypto.bot.telegram.converters;

import com.crypto.bot.telegram.entity.UpdateEntity;
import com.pengrad.telegrambot.model.Update;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateToEntityConverter implements Converter<Update, UpdateEntity> {
    @Override
    public UpdateEntity convert(Update source) {
        return new UpdateEntity()
                .setId(source.message().messageId())
                .setText(source.message().text())
                .setChatId(source.message().chat().id());
    }
}
