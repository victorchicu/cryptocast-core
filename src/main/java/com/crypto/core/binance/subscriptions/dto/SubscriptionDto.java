package com.crypto.core.binance.subscriptions.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SubscriptionDto {
    private final String id;
    private final String symbolName;

    @JsonCreator
    public SubscriptionDto(String id, String symbolName) {
        this.id = id;
        this.symbolName = symbolName;
    }

    public String getId() {
        return id;
    }

    public String getSymbolName() {
        return symbolName;
    }
}
