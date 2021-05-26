package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.repository.CustomSymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.SymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.cryptostrophe.bot.services.SymbolTickerEventService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SymbolTickerEventServiceImpl implements SymbolTickerEventService {
    private final SymbolTickerEventRepository symbolTickerEventRepository;

    public SymbolTickerEventServiceImpl(SymbolTickerEventRepository symbolTickerEventRepository) {
        this.symbolTickerEventRepository = symbolTickerEventRepository;
    }

    @Override
    public void updateSymbolTickerEvent(Integer participantId, String symbol, Long eventTime) {
        symbolTickerEventRepository.findSymbolTickerEvent(participantId, symbol)
                .ifPresent((SymbolTickerEvent symbolTickerEvent) -> {
                    symbolTickerEvent.setEventTime(eventTime);
                    symbolTickerEventRepository.save(symbolTickerEvent);
                });
    }

    @Override
    public void deleteSymbolTickerEvent(Integer participant, String symbol) {
        Optional<SymbolTickerEvent> optional = symbolTickerEventRepository.findSymbolTickerEvent(participant, symbol);
        optional.ifPresent(event -> symbolTickerEventRepository.deleteById(event.getId()));
    }

    @Override
    public SymbolTickerEvent saveSymbolTickerEvent(SymbolTickerEvent symbolTickerEvent) {
        return symbolTickerEventRepository.save(symbolTickerEvent);
    }

    @Override
    public Optional<SymbolTickerEvent> findSymbolTickerEvent(Integer participantId, String symbol) {
        return symbolTickerEventRepository.findSymbolTickerEvent(participantId, symbol);
    }
}
