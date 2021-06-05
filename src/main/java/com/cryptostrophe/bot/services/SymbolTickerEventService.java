package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;

import java.util.Optional;

public interface SymbolTickerEventService {
    void deleteSymbolTickerEvent(Integer participantId, String symbol);

    void updateSymbolTickerEvent(Integer participantId, String symbol, Long eventTime);

    SymbolTickerEventEntity saveSymbolTickerEvent(SymbolTickerEventEntity symbolTickerEvent);

    Optional<SymbolTickerEventEntity> findSymbolTickerEvent(Integer participantId, String symbol);
}
