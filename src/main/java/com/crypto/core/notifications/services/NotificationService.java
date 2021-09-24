package com.crypto.core.notifications.services;

import com.crypto.core.notifications.domain.Notification;

public interface NotificationService {
    Notification saveNotification(Notification notification);
}
