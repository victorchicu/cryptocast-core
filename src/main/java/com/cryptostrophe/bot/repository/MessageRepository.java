package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.MessageDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageDocument, String> {
}
