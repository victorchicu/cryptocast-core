package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AssetChipDto {
    private final String assetName;

    @JsonCreator
    public AssetChipDto(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
