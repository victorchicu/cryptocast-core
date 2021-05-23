package com.cryptostrophe.bot.repository.model;

public class SymbolTickerEvent {
    private Long eventTime;
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
