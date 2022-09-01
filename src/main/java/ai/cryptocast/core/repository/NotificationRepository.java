package ai.cryptocast.core.repository;

import ai.cryptocast.core.repository.entity.NotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, String> {

}
