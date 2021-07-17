package com.crypto.cli.notifications.repository;

import com.crypto.cli.notifications.repository.entity.NotificationRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationRequestEntity, String> {

}
