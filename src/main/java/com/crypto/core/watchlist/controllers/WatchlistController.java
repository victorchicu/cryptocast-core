package com.crypto.core.watchlist.controllers;

import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.watchlist.domain.Subscription;
import com.crypto.core.watchlist.dto.SubscriptionDto;
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

    public WatchlistController(
            BinanceService binanceService,
            WatchlistService watchlistService,
            ConversionService conversionService
    ) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.watchlistService = watchlistService;
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return watchlistService.findSubscription(principal, assetName)
                .map(this::toSubscriptionDto)
                .orElseGet(() -> {
                    Subscription subscription = watchlistService.saveSubscription(
                            Subscription.newBuilder()
                                    .assetName(assetName)
                                    .build()
                    );
                    binanceService.subscribeOnTickerEvent(assetName, (TickerEvent tickerEvent) -> {
                        //TODO: Push ticker event data to websocket
                        System.out.println(tickerEvent);
                    });
                    return toSubscriptionDto(subscription);
                });
    }


    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return watchlistService.findSubscription(principal, assetName)
                .map(subscription -> {
                    binanceService.unsubscribeFromTickerEvent(assetName);
                    watchlistService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }

    @GetMapping
    public Page<SubscriptionDto> listSubscriptions(Principal principal, Pageable pageable) {
        return watchlistService.findSubscriptions(principal, pageable)
                .map(this::toSubscriptionDto);
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }
}
