package com.trader.core.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = SubscriptionEntity.COLLECTION_NAME)
public class SubscriptionEntity extends BaseEntity {
    public static final String COLLECTION_NAME = "subscriptions";

    private String fundsName;

    public SubscriptionEntity() {}

    private SubscriptionEntity(Builder builder) {fundsName = builder.fundsName;}

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getFundsName() {
        return fundsName;
    }

    public void setFundsName(String fundsName) {
        this.fundsName = fundsName;
    }

    public static class Field {
        public static final String FUNDS_NAME = "fundsName";
    }

    public static final class Builder {
        private String fundsName;

        private Builder() {}

        public Builder symbolName(String fundsName) {
            this.fundsName = fundsName;
            return this;
        }

        public SubscriptionEntity build() {
            return new SubscriptionEntity(this);
        }
    }
}
