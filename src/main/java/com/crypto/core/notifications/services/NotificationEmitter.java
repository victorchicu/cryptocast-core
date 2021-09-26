package com.crypto.core.notifications.services;

import com.crypto.core.notifications.enums.NotificationEvent;

import java.security.Principal;

public interface NotificationEmitter {
    <T> T emitNotification(Principal principal, NotificationEvent event, T payload, Class<?> classType);
}
