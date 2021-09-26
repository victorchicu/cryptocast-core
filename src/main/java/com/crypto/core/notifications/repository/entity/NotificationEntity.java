package com.crypto.core.notifications.repository.entity;

import com.crypto.core.notifications.enums.NotificationEvent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "notifications")
public class NotificationEntity extends BaseEntity {
    private NotificationEvent event;
    private Map<String, Object> payload;

    private NotificationEntity(Builder builder) {
        event = builder.event;
        payload = builder.payload;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public NotificationEvent getEvent() {
        return event;
    }

    public void setEvent(NotificationEvent event) {
        this.event = event;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public static final class Builder {
        private NotificationEvent event;
        private Map<String, Object> payload;

        private Builder() {}

        public Builder event(NotificationEvent event) {
            this.event = event;
            return this;
        }

        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        public NotificationEntity build() {
            return new NotificationEntity(this);
        }
    }
}
