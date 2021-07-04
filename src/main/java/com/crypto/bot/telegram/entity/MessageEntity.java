package com.crypto.bot.telegram.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = MessageEntity.COLLECTION_NAME)
public class MessageEntity {
    public static final String COLLECTION_NAME = "messages";

    @Id
    private Integer id;
    private Long chatId;
    private String text;

    public Integer getId() {
        return id;
    }

    public MessageEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public MessageEntity setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageEntity setText(String text) {
        this.text = text;
        return this;
    }
}
