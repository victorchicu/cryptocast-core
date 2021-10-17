package com.crypto.core.notifications.services;

import com.crypto.core.notifications.enums.NotificationType;

import java.security.Principal;

public interface NotificationTemplate {
    <T> void sendNotification(
            Principal principal,
            NotificationType type,
            T payload,
            Class<?> classType
    );
}
