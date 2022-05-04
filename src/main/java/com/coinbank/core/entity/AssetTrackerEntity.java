package com.coinbank.core.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = AssetTrackerEntity.COLLECTION_NAME)
public class AssetTrackerEntity extends BaseEntity {
    public static final String COLLECTION_NAME = "tracking";

    private String assetName;

    public AssetTrackerEntity() {}

    private AssetTrackerEntity(Builder builder) {assetName = builder.assetName;}

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public static class Field {
        public static final String ASSET_NAME = "assetName";
    }

    public static final class Builder {
        private String assetName;

        private Builder() {}

        public Builder symbolName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public AssetTrackerEntity build() {
            return new AssetTrackerEntity(this);
        }
    }
}
