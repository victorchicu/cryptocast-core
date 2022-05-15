package com.coinbank.core.domain.services;

import com.coinbank.core.domain.Notification;

public interface NotificationService {
    <T> Notification saveNotification(T payload);
}
