package com.crypto.bot.notifications.repository;

import com.crypto.bot.notifications.repository.entity.NotificationRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationRequestEntity, String> {

}
