package com.crypto.core.watchlist.repository.impl;

import com.crypto.core.watchlist.domain.Watchlist;
import com.crypto.core.watchlist.repository.CustomWatchlistRepository;
import com.crypto.core.watchlist.entity.WatchlistEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomWatchlistRepositoryImpl implements CustomWatchlistRepository {
    private final MongoOperations mongoOperations;
    private final ConversionService conversionService;

    public CustomWatchlistRepositoryImpl(MongoOperations mongoOperations, ConversionService conversionService) {
        this.mongoOperations = mongoOperations;
        this.conversionService = conversionService;
    }

    @Override
    public void remove(Principal principal) {
        mongoOperations.remove(
                Query.query(Criteria.where(WatchlistEntity.Field.REQUESTER_ID).is(principal)),
                WatchlistEntity.class,
                WatchlistEntity.COLLECTION_NAME
        );
    }

    @Override
    public void update(Query query, String propertyKey, Object propertyValue) {
        mongoOperations.updateFirst(
                query,
                Update.update(propertyKey, propertyValue),
                WatchlistEntity.class,
                WatchlistEntity.COLLECTION_NAME
        );
    }

    @Override
    public Page<Watchlist> list(Principal principal, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(WatchlistEntity.Field.REQUESTER_ID).is(principal);

        List<WatchlistEntity> watchlist = mongoOperations.find(
                Query.query(matchCriteria),
                WatchlistEntity.class,
                WatchlistEntity.COLLECTION_NAME
        );

        //TODO:

        return PageableExecutionUtils.getPage(
                watchlist.stream().map(this::toWatchlist).collect(Collectors.toList()),
                pageable,
                () -> 0
        );
    }

    @Override
    public Page<Watchlist> list(Principal principal, List<String> symbolNames, Pageable pageable) {
        Criteria matchCriteria = Criteria.where(WatchlistEntity.Field.REQUESTER_ID)
                .is(principal)
                .and(WatchlistEntity.Field.SYMBOL_NAME)
                .in(symbolNames);

        List<WatchlistEntity> watchlistEntities = mongoOperations.find(
                Query.query(matchCriteria),
                WatchlistEntity.class,
                WatchlistEntity.COLLECTION_NAME
        );

        return PageableExecutionUtils.getPage(
                watchlistEntities.stream().map(this::toWatchlist).collect(Collectors.toList()),
                pageable, () -> 0
        );
    }

    @Override
    public Optional<Watchlist> find(Principal principal, String symbolName) {
        Criteria matchCriteria = Criteria.where(WatchlistEntity.Field.REQUESTER_ID)
                .is(principal)
                .and(WatchlistEntity.Field.SYMBOL_NAME)
                .is(symbolName);

        WatchlistEntity watchlistEntity = mongoOperations.findOne(
                Query.query(matchCriteria),
                WatchlistEntity.class,
                WatchlistEntity.COLLECTION_NAME
        );

        return Optional.ofNullable(watchlistEntity).map(this::toWatchlist);
    }


    private Watchlist toWatchlist(WatchlistEntity watchlistEntity) {
        return conversionService.convert(watchlistEntity, Watchlist.class);
    }
}
