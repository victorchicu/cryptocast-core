package com.coinbank.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NotificationDto {
    private final String id;

    @JsonCreator
    public NotificationDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
