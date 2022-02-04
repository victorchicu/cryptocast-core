package com.trader.core.domain;

public class Subscription {
    private String id;
    private String fundsName;

    private Subscription(Builder builder) {
        id = builder.id;
        fundsName = builder.fundsName;
    }

    public String getId() {
        return id;
    }

    public String getFundsName() {
        return fundsName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String fundsName;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fundsName(String fundsName) {
            this.fundsName = fundsName;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }
}
