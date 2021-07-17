package com.crypto.core.repository.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = SubscriptionEntity.COLLECTION_NAME)
public class SubscriptionEntity {
    public static final String COLLECTION_NAME = "subscriptions";

    @Id
    private String id;
    private Long chatId;
    private Long updatedAt;
    private String symbolName;
    private Integer messageId;
    private Integer participantId;
    @CreatedDate
    private Instant createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public SubscriptionEntity setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public SubscriptionEntity setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public SubscriptionEntity setSymbolName(String symbolName) {
        this.symbolName = symbolName;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public SubscriptionEntity setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public SubscriptionEntity setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class Field {
        public static final String ID = "_id";
        public static final String CHAT_ID = "chatId";
        public static final String UPDATED_AT = "updatedAt";
        public static final String SYMBOL_NAME = "symbolName";
        public static final String MESSAGE_ID = "messageId";
        public static final String PARTICIPANT_ID = "participantId";
    }
}
