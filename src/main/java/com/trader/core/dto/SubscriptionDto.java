package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SubscriptionDto {
    private final String id;
    private final String assetName;

    @JsonCreator
    public SubscriptionDto(String id, String assetName) {
        this.id = id;
        this.assetName = assetName;
    }

    public String getId() {
        return id;
    }

    public String getAssetName() {
        return assetName;
    }
}
