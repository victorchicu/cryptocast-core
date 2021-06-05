package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends CrudRepository<ParticipantSubscriptionEntity, String>, CustomParticipantsRepository {
}
