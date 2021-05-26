package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.mongodb.client.result.DeleteResult;

import java.util.Optional;

public interface SymbolTickerEventService {
    void deleteSymbolTickerEvent(Integer participantId, String symbol);

    void updateSymbolTickerEvent(Integer participantId, String symbol, Long eventTime);

    SymbolTickerEvent saveSymbolTickerEvent(SymbolTickerEvent symbolTickerEvent);

    Optional<SymbolTickerEvent> findSymbolTickerEvent(Integer participantId, String symbol);
}
