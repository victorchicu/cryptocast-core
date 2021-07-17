package com.crypto.bot.notifications.services;

import com.crypto.bot.notifications.domain.NotificationRequest;

public interface NotificationService {
    NotificationRequest saveNotification(NotificationRequest notificationRequest);
}
