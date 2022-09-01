package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.User;
import ai.cryptocast.core.enums.NotificationType;

public interface NotificationTemplate {
    <T> void sendNotification(User user, NotificationType type, T payload);
}
