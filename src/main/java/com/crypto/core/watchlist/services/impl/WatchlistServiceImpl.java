package com.crypto.core.watchlist.services.impl;

import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.entity.SubscriptionEntity;
import com.crypto.core.watchlist.repository.WatchlistRepository;
import com.crypto.core.watchlist.services.WatchlistService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public void deleteWatchlist(Principal principal) {
        watchlistRepository.removeWatchlsit(principal);
    }

    @Override
    public void deleteSubscriptionById(String watchlistId) {
        watchlistRepository.deleteById(watchlistId);
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = toSubscriptionEntity(subscription);
        subscription = toSubscription(watchlistRepository.save(subscriptionEntity));
        return subscription;
    }

    @Override
    public Page<Subscription> findSubscriptions(Principal principal, Pageable pageable) {
        return watchlistRepository.listSubscriptions(principal, pageable);
    }

    @Override
    public Page<Subscription> findSubscriptions(Principal principal, List<String> assetNames, Pageable pageable) {
        return watchlistRepository.listSubscriptions(principal, assetNames, pageable);
    }

    @Override
    public Optional<Subscription> findSubscription(Principal principal, String assetName) {
        return watchlistRepository.findSubscription(principal, assetName);
    }


    private Subscription toSubscription(SubscriptionEntity subscriptionEntity) {
        return conversionService.convert(subscriptionEntity, Subscription.class);
    }

    private SubscriptionEntity toSubscriptionEntity(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionEntity.class);
    }
}
