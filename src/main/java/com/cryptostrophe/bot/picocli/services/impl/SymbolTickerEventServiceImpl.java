package com.cryptostrophe.bot.picocli.services.impl;

import com.cryptostrophe.bot.repository.SymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import com.cryptostrophe.bot.picocli.services.SymbolTickerEventService;
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
                .ifPresent((SymbolTickerEventEntity symbolTickerEvent) -> {
                    symbolTickerEvent.setEventTime(eventTime);
                    symbolTickerEventRepository.save(symbolTickerEvent);
                });
    }

    @Override
    public void deleteSymbolTickerEvent(Integer participantId, String symbol) {
        Optional<SymbolTickerEventEntity> optional = symbolTickerEventRepository.findSymbolTickerEvent(participantId, symbol);
        optional.ifPresent(event -> symbolTickerEventRepository.deleteById(event.getId()));
    }

    @Override
    public SymbolTickerEventEntity saveSymbolTickerEvent(SymbolTickerEventEntity symbolTickerEvent) {
        return symbolTickerEventRepository.save(symbolTickerEvent);
    }

    @Override
    public Optional<SymbolTickerEventEntity> findSymbolTickerEvent(Integer participantId, String symbol) {
        return symbolTickerEventRepository.findSymbolTickerEvent(participantId, symbol);
    }
}
