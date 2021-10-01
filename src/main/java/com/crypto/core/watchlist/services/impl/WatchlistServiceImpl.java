package com.crypto.core.watchlist.services.impl;

import com.crypto.core.watchlist.domain.Watchlist;
import com.crypto.core.watchlist.entity.WatchlistEntity;
import com.crypto.core.watchlist.repository.WatchlistRepository;
import com.crypto.core.watchlist.services.WatchlistService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final ConversionService conversionService;
    private final WatchlistRepository watchlistRepository;

    public WatchlistServiceImpl(ConversionService conversionService, WatchlistRepository watchlistRepository) {
        this.conversionService = conversionService;
        this.watchlistRepository = watchlistRepository;
    }

    @Override
    public void update(Query query, String updateKey, Object updateValue) {
        watchlistRepository.update(query, updateKey, updateValue);
    }

    @Override
    public void deleteAll(Principal principal) {
        watchlistRepository.remove(principal);
    }

    @Override
    public void deleteById(String id) {
        watchlistRepository.deleteById(id);
    }

    @Override
    public Watchlist save(Watchlist watchlist) {
        WatchlistEntity watchlistEntity = toWatchlistEntity(watchlist);
        watchlist = toWatchlist(watchlistRepository.save(watchlistEntity));
        return watchlist;
    }

    @Override
    public Page<Watchlist> findAll(Principal principal, Pageable pageable) {
        return watchlistRepository.list(principal, pageable);
    }

    @Override
    public Page<Watchlist> findAll(Principal principal, List<String> symbolNames, Pageable pageable) {
        return watchlistRepository.list(principal, symbolNames, pageable);
    }

    @Override
    public Page<Watchlist> findAll(Pageable pageable) {
        List<WatchlistEntity> watchlist = IterableUtils.toList(watchlistRepository.findAll());
        return PageableExecutionUtils.getPage(
                watchlist.stream().map(this::toWatchlist).collect(Collectors.toList()),
                pageable,
                () -> watchlist.size()
        );
    }

    @Override
    public Optional<Watchlist> find(Principal principal, String symbolName) {
        return watchlistRepository.find(principal, symbolName);
    }


    private Watchlist toWatchlist(WatchlistEntity watchlistEntity) {
        return conversionService.convert(watchlistEntity, Watchlist.class);
    }

    private WatchlistEntity toWatchlistEntity(Watchlist watchlist) {
        return conversionService.convert(watchlist, WatchlistEntity.class);
    }
}
