package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SubscriptionDto {
    private final String id;
    private final String fundsName;

    @JsonCreator
    public SubscriptionDto(String id, String fundsName) {
        this.id = id;
        this.fundsName = fundsName;
    }

    public String getId() {
        return id;
    }

    public String getFundsName() {
        return fundsName;
    }
}
