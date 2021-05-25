package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolTickerEventRepository extends CrudRepository<SymbolTickerEvent, String>, CustomSymbolTickerEventRepository {
}