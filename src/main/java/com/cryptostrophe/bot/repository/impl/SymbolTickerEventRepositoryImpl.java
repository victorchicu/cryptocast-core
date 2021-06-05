package com.cryptostrophe.bot.repository.impl;

import com.cryptostrophe.bot.repository.CustomSymbolTickerEventRepository;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SymbolTickerEventRepositoryImpl implements CustomSymbolTickerEventRepository {
    private final MongoOperations mongoOperations;

    public SymbolTickerEventRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Optional<SymbolTickerEventEntity> findSymbolTickerEvent(Integer participantId, String symbol) {
        Query query = Query.query(Criteria.where("symbol").is(symbol).and("participantId").is(participantId));
        return Optional.ofNullable(mongoOperations.findOne(query, SymbolTickerEventEntity.class, "symbol_ticker_events"));
    }
}
