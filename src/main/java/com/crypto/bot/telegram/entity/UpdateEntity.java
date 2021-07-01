package com.crypto.bot.telegram.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "updates")
public class UpdateEntity {
    @Id
    private Integer id;
    private Long chatId;

    public Integer getId() {
        return id;
    }

    public UpdateEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public UpdateEntity setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }
}
