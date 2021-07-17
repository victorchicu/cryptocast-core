package com.crypto.cli.telegram.converters;

import com.crypto.cli.telegram.entity.MessageEntity;
import com.pengrad.telegrambot.model.Message;
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
