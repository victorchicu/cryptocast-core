package com.trader.core.domain;

public class Subscription {
    private String id;
    private String assetName;

    private Subscription(Builder builder) {
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

        public Builder assetName(String symbolName) {
            this.assetName = symbolName;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }
}
