package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ChipDto {
    private final String assetName;

    @JsonCreator
    public ChipDto(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
