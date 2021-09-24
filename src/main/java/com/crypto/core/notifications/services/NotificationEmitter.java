package com.crypto.core.notifications.services;

public interface NotificationEmitter {
    <T> void emit(String type, T notification);
}
