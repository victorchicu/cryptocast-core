package com.cryptostrophe.bot.repository.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "symbol_ticker_events")
public class SymbolTickerEvent {
    @Field("event_time")
    private Long eventTime;
    @Field("participant_id")
    private Integer participantId;

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
