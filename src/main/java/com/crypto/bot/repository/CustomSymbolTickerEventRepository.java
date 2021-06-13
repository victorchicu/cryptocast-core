package com.crypto.bot.repository;

import com.crypto.bot.repository.model.SymbolTickerEventEntity;

import java.util.Optional;

public interface CustomSymbolTickerEventRepository {
    Optional<SymbolTickerEventEntity> findSymbolTickerEvent(Integer participantId, String symbol);
}
