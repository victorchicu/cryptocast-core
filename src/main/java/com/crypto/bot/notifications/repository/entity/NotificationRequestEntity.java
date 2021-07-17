package com.crypto.bot.notifications.repository.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "notification_requests")
public class NotificationRequestEntity extends AbstractEntity {
    private String type;
    private Map<String, Object> payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
