package com.crypto.bot.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
