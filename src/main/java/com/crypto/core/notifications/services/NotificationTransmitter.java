package com.crypto.core.notifications.services;

import com.crypto.core.notifications.domain.NotificationRequest;

public interface NotificationTransmitter {
    void sendNotification(NotificationRequest notificationRequest);
}
