package com.crypto.cli.notifications.services;

import com.crypto.cli.notifications.domain.NotificationRequest;

public interface NotificationTransmitter {
    void sendNotification(NotificationRequest notificationRequest);
}
