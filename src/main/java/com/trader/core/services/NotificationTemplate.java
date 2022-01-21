package com.trader.core.services;

import com.trader.core.enums.NotificationType;

import java.security.Principal;

public interface NotificationTemplate {
    <T> void sendNotification(Principal principal, NotificationType type, T payload);
}
