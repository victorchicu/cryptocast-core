package com.trader.core.notifications.entity;

import com.trader.core.notifications.enums.NotificationType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "notifications")
public class NotificationEntity extends BaseEntity {
    private NotificationType type;
    private Map<String, Object> payload;

    private NotificationEntity(Builder builder) {
        type = builder.type;
        payload = builder.payload;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public static final class Builder {
        private NotificationType type;
        private Map<String, Object> payload;

        private Builder() {}

        public Builder event(NotificationType type) {
            this.type = type;
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
