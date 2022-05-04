package com.coinbank.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AssetTrackerDto {
    private final String id;
    private final String assetName;

    @JsonCreator
    public AssetTrackerDto(String id, String assetName) {
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
