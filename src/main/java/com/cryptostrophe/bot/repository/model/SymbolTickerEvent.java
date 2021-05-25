package com.cryptostrophe.bot.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("symbol_ticker_events")
public class SymbolTickerEvent {
    @Id
    private String id;
    @Field("symbol")
    private String symbol;
    @Field("event_time")
    private Long eventTime;
    @Field("participant_id")
    private Integer participantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public SymbolTickerEvent setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public SymbolTickerEvent setEventTime(Long eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public SymbolTickerEvent setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }
}
