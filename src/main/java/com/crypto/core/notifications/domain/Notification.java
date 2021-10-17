package com.crypto.core.notifications.domain;

import com.crypto.core.notifications.enums.NotificationType;

import java.util.Map;

public class Notification {
    private final String id;
    private final NotificationType type;
    private final Class<?> classType;
    private final Map<String, Object> payloadProps;

    public Notification(String id, NotificationType type, Class<?> classType, Map<String, Object> payloadProps) {
        this.id = id;
        this.type = type;
        this.classType = classType;
        this.payloadProps = payloadProps;
    }

    public String getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public Map<String, Object> getPayloadProps() {
        return payloadProps;
    }
}
