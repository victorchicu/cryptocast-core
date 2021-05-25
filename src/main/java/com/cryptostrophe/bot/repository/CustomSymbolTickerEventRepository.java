package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;

import java.util.Optional;

public interface CustomSymbolTickerEventRepository {
    Optional<SymbolTickerEvent> findSymbolTickerEvent(Integer participantId, String symbol);
}
