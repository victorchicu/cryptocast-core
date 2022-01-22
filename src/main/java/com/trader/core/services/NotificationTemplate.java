package com.trader.core.services;

import com.trader.core.domain.User;
import com.trader.core.enums.NotificationType;

public interface NotificationTemplate {
    <T> void sendNotification(User user, NotificationType type, T payload);
}
