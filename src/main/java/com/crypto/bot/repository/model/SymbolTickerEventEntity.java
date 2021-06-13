package com.crypto.bot.repository.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("symbol_ticker_events")
public class SymbolTickerEventEntity extends BaseEntity {
    private Long eventTime;
    private String symbol;
    private Integer participantId;

    public String getSymbol() {
        return symbol;
    }

    public SymbolTickerEventEntity setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public SymbolTickerEventEntity setEventTime(Long eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public SymbolTickerEventEntity setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }
}
