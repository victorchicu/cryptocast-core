package com.crypto.core.watchlist.controllers;

import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.binance.dto.SymbolTickerEventDto;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.notifications.enums.NotificationEvent;
import com.crypto.core.notifications.services.NotificationEmitter;
import com.crypto.core.notifications.services.NotificationService;
import com.crypto.core.watchlist.domain.Watchlist;
import com.crypto.core.watchlist.dto.WatchlistDto;
import com.crypto.core.watchlist.exceptions.SubscriptionNotFoundException;
import com.crypto.core.watchlist.services.WatchlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchlistController.class);

    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final ConversionService conversionService;
    private final NotificationService notificationService;
    private final NotificationEmitter notificationEmitter;

    public WatchlistController(
            BinanceService binanceService,
            WatchlistService watchlistService,
            ConversionService conversionService,
            NotificationService notificationService,
            NotificationEmitter notificationEmitter
    ) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.notificationService = notificationService;
        this.notificationEmitter = notificationEmitter;
        this.watchlistService = watchlistService;
    }

    @PutMapping("/{symbolName}/add")
    public WatchlistDto add(Principal principal, @PathVariable String symbolName) {
        return watchlistService.find(principal, symbolName)
                .map(this::toWatchlistDto)
                .orElseGet(() -> {
                    Watchlist watchlist = watchlistService.save(
                            Watchlist.newBuilder()
                                    .symbolName(symbolName)
                                    .build()
                    );
                    binanceService.subscribe(
                            principal,
                            symbolName,
                            emitSymbolTickerEventCallback(principal),
                            handleSubscriptionError()
                    );
                    return toWatchlistDto(watchlist);
                });
    }


    @DeleteMapping("/{symbolName}/remove")
    public WatchlistDto remove(Principal principal, @PathVariable String symbolName) {
        return watchlistService.find(principal, symbolName)
                .map(subscription -> {
                    binanceService.unsubscribe(principal, symbolName);
                    watchlistService.deleteById(subscription.getId());
                    return toWatchlistDto(subscription);
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }

    @GetMapping
    public Page<WatchlistDto> list(Pageable pageable) {
        return watchlistService.findAll(pageable).map(this::toWatchlistDto);
    }


    private WatchlistDto toWatchlistDto(Watchlist watchlist) {
        return conversionService.convert(watchlist, WatchlistDto.class);
    }

    private SubscriptionErrorHandler handleSubscriptionError() {
        return e -> LOGGER.error(e.getMessage(), e);
    }

    private SubscriptionListener<SymbolTickerEvent> emitSymbolTickerEventCallback(Principal principal) {
        return (SymbolTickerEvent symbolTickerEvent) -> {
            notificationEmitter.emitNotification(
                    principal,
                    NotificationEvent.SYMBOL_TICKER_EVENT,
                    symbolTickerEvent,
                    SymbolTickerEventDto.class
            );
            notificationService.saveNotification(symbolTickerEvent);
        };
    }
}
