package com.crypto.core.notifications.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class NotificationRequestDto {
    private final String id;
    private final String type;
    private final Map<String, Object> payload;

    @JsonCreator
    public NotificationRequestDto(String id, String type, Map<String, Object> payload) {
        this.id = id;
        this.type = type;
        this.payload = payload;
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
}
