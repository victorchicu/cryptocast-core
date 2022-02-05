package com.trader.core.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = SubscriptionEntity.COLLECTION_NAME)
public class SubscriptionEntity extends BaseEntity {
    public static final String COLLECTION_NAME = "subscriptions";

    private String assetName;

    public SubscriptionEntity() {}

    private SubscriptionEntity(Builder builder) {assetName = builder.assetName;}

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

        public SubscriptionEntity build() {
            return new SubscriptionEntity(this);
        }
    }
}
