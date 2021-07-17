package com.crypto.bot.notifications.services;

import com.crypto.bot.notifications.domain.NotificationRequest;

public interface NotificationTransmitter {
    void sendNotification(NotificationRequest notificationRequest);
}
