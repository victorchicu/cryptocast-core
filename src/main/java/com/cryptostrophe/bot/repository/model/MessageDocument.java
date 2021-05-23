package com.cryptostrophe.bot.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "messages")
public class MessageDocument {
    @Id
    private String id;
    @Field("chat_id")
    private Long chatId;
    @Field("message_id")
    private Integer messageId;
    @Field("created_at")
    @CreatedDate
    private Instant createdAt;

    public String getId() {
        return id;
    }

    public MessageDocument setId(String id) {
        this.id = id;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public MessageDocument setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public MessageDocument setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public MessageDocument setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return "MessageDocument{" +
                "id='" + id + '\'' +
                ", chatId=" + chatId +
                ", messageId=" + messageId +
                ", createdAt=" + createdAt +
                '}';
    }
}
