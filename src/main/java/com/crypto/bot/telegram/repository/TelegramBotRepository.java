package com.crypto.bot.telegram.repository;

import com.crypto.bot.telegram.entity.UpdateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramBotRepository extends CrudRepository<UpdateEntity, Integer> {
}
