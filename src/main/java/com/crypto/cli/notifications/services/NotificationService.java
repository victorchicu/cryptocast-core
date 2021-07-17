package com.crypto.cli.notifications.services;

import com.crypto.cli.notifications.domain.NotificationRequest;

public interface NotificationService {
    NotificationRequest saveNotification(NotificationRequest notificationRequest);
}
