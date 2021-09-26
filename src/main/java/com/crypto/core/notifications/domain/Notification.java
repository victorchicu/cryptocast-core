package com.crypto.core.notifications.domain;

import com.crypto.core.notifications.enums.NotificationEvent;

import java.util.Map;

public class Notification {
    private final String id;
    private final NotificationEvent event;
    private final Map<String, Object> payload;

    private Notification(Builder builder) {
        id = builder.id;
        event = builder.event;
        payload = builder.payload;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public NotificationEvent getEvent() {
        return event;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public static final class Builder {
        private String id;
        private NotificationEvent event;
        private Map<String, Object> payload;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder event(NotificationEvent event) {
            this.event = event;
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
