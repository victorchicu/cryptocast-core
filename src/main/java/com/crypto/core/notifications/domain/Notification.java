package com.crypto.core.notifications.domain;

import java.util.Map;

public class Notification {
    private final String id;
    private final String type;
    private final Map<String, Object> payload;

    private Notification(Builder builder) {
        id = builder.id;
        type = builder.type;
        payload = builder.payload;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public static final class Builder {
        private String id;
        private String type;
        private Map<String, Object> payload;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
