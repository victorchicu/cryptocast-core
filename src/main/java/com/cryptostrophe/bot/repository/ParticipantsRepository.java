package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends CrudRepository<ParticipantSubscription, String>, CustomParticipantsRepository {
}
