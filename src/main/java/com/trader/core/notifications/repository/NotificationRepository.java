package com.trader.core.notifications.repository;

import com.trader.core.notifications.entity.NotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, String> {

}
