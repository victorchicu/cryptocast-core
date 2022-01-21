package com.trader.core.services;

import com.trader.core.domain.Notification;

public interface NotificationService {
    <T> Notification saveNotification(T payload);
}
