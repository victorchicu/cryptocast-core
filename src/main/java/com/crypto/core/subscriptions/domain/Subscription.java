package com.crypto.core.subscriptions.domain;

public class Subscription {
    private String id;
    private String symbolName;

    private Subscription(Builder builder) {
        id = builder.id;
        symbolName = builder.symbolName;
    }

    public String getId() {
        return id;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String symbolName;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder symbolName(String symbolName) {
            this.symbolName = symbolName;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }
}
