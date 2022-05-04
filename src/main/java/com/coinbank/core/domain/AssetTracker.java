package com.coinbank.core.domain;

public class AssetTracker {
    private String id;
    private String assetName;

    private AssetTracker(Builder builder) {
        id = builder.id;
        assetName = builder.assetName;
    }

    public String getId() {
        return id;
    }

    public String getAssetName() {
        return assetName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String assetName;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder assetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public AssetTracker build() {
            return new AssetTracker(this);
        }
    }
}
