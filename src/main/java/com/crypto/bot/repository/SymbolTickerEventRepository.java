package com.crypto.bot.repository;

import com.crypto.bot.repository.model.SymbolTickerEventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolTickerEventRepository extends CrudRepository<SymbolTickerEventEntity, String>, CustomSymbolTickerEventRepository {
}
