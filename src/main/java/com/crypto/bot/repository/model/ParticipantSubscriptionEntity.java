package com.crypto.bot.repository.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "participant_subscriptions")
public class ParticipantSubscriptionEntity extends BaseEntity {
    private Long chatId;
    private String symbol;
    private Integer messageId;
    private Integer participantId;

    public String getSymbol() {
        return symbol;
    }

    public ParticipantSubscriptionEntity setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public ParticipantSubscriptionEntity setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public ParticipantSubscriptionEntity setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public ParticipantSubscriptionEntity setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }
}
