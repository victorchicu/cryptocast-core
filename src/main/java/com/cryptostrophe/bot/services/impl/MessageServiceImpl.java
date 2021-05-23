package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.repository.MessageRepository;
import com.cryptostrophe.bot.repository.model.MessageDocument;
import com.cryptostrophe.bot.services.MessageService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDocument save(MessageDocument message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageDocument> findAll(String[] symbols) {
        Iterable<MessageDocument> it = messageRepository.findAllById(Arrays.asList(symbols));
        return IterableUtils.toList(it);
    }

    @Override
    public Optional<MessageDocument> findById(String symbol) {
        return messageRepository.findById(symbol);
    }
}
