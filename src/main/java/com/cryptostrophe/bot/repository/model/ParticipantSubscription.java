package com.cryptostrophe.bot.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "participant_subscriptions")
public class ParticipantSubscription {
    @Id
    private String id;
    @Field("chat_id")
    private Long chatId;
    @Field("symbol")
    private String symbol;
    @Field("message_id")
    private Integer messageId;
    @Field("participant_id")
    private Integer participantId;

    public String getId() {
        return id;
    }

    public ParticipantSubscription setId(String id) {
        this.id = id;
        return this;
    }

    public Long getChatId() {
        return chatId;
    }

    public ParticipantSubscription setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public ParticipantSubscription setSymbol(String symbol) {
        this.symbol = symbol;
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
