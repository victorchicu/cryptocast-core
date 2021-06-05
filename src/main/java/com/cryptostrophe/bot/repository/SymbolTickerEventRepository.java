package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolTickerEventRepository extends CrudRepository<SymbolTickerEventEntity, String>, CustomSymbolTickerEventRepository {
}
