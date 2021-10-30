package com.trader.core.notifications.services;

import com.trader.core.notifications.domain.Notification;

public interface NotificationService {
    <T> Notification saveNotification(T payload);
}
