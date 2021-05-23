package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SymbolTickerEventRepository extends CrudRepository<SymbolTickerEvent, Integer> {
    Optional<SymbolTickerEvent> findByParticipantIdAndSymbol(Integer participantId, String symbol);
}
