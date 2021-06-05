package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;

import java.util.Optional;

public interface CustomSymbolTickerEventRepository {
    Optional<SymbolTickerEventEntity> findSymbolTickerEvent(Integer participantId, String symbol);
}
