package com.crypto.cli.telegram.repository;

import com.crypto.cli.telegram.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramBotRepository extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByChatId(Long chatId);
}
