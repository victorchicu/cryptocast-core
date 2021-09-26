package com.crypto.core.notifications.services;

import com.crypto.core.notifications.domain.Notification;

public interface NotificationService {
    <T> Notification saveNotification(T payload);
}
