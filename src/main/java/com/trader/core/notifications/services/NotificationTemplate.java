package com.trader.core.notifications.services;

import com.trader.core.notifications.enums.NotificationType;

import java.security.Principal;

public interface NotificationTemplate {
    <T> void sendNotification(Principal principal, NotificationType type, T payload);
}
