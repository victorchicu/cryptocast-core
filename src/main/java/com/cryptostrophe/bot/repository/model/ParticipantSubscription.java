package com.cryptostrophe.bot.repository.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "participant_subscriptions")
public class ParticipantSubscription extends BaseEntity {
    private Long chatId;
    private String symbol;
    private Integer messageId;
    private Integer participantId;

    public String getSymbol() {
        return symbol;
    }

    public ParticipantSubscription setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public ParticipantSubscription setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public ParticipantSubscription setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public ParticipantSubscription setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }
}
