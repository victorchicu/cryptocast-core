package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.Notification;

public interface NotificationService {
    <T> Notification saveNotification(T payload);
}
