package com.crypto.bot.telegram.converters;

import com.crypto.bot.telegram.entity.MessageEntity;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToEntityConverter implements Converter<Message, MessageEntity> {
    @Override
    public MessageEntity convert(Message source) {
        return new MessageEntity()
                .setId(source.messageId())
                .setText(source.text())
                .setChatId(source.chat().id());
    }
}
