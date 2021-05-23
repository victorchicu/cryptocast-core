package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.MessageDocument;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    MessageDocument save(MessageDocument message);
    List<MessageDocument> findAll(String[] symbols);
    Optional<MessageDocument> findById(String symbol);
}
