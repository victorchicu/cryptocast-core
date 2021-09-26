package com.crypto.core.subscriptions.controllers;

import com.crypto.core.exchanges.binance.client.SubscriptionErrorHandler;
import com.crypto.core.exchanges.binance.client.SubscriptionListener;
import com.crypto.core.exchanges.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.exchanges.binance.dto.SymbolTickerEventDto;
import com.crypto.core.exchanges.binance.services.BinanceService;
import com.crypto.core.notifications.enums.NotificationEvent;
import com.crypto.core.notifications.services.NotificationEmitter;
import com.crypto.core.notifications.services.NotificationService;
import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.dto.SubscriptionDto;
import com.crypto.core.subscriptions.exceptions.SubscriptionNotFoundException;
import com.crypto.core.subscriptions.services.SubscriptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsController.class);

    private final BinanceService binanceService;
    private final ConversionService conversionService;
    private final NotificationService notificationService;
    private final NotificationEmitter notificationEmitter;
    private final SubscriptionsService subscriptionsService;

    public SubscriptionsController(
            BinanceService binanceService,
            ConversionService conversionService,
            NotificationService notificationService,
            NotificationEmitter notificationEmitter,
            SubscriptionsService subscriptionsService
    ) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.notificationService = notificationService;
        this.notificationEmitter = notificationEmitter;
        this.subscriptionsService = subscriptionsService;
    }

    @PutMapping("/{symbolName}/subscribe")
    public SubscriptionDto subscribe(Principal principal, @PathVariable String symbolName) {
        return subscriptionsService.findSubscription(principal, symbolName)
                .map(this::toSubscriptionDto)
                .orElseGet(() -> {
                    Subscription subscription = subscriptionsService.saveSubscription(
                            Subscription.newBuilder()
                                    .symbolName(symbolName)
                                    .build()
                    );
                    binanceService.subscribe(
                            principal,
                            symbolName,
                            emitSymbolTickerEventCallback(principal),
                            handleSubscriptionError()
                    );
                    return toSubscriptionDto(subscription);
                });
    }


    @DeleteMapping("/{symbolName}/unsubscribe")
    public SubscriptionDto unsubscribe(Principal principal, @PathVariable String symbolName) {
        return subscriptionsService.findSubscription(principal, symbolName)
                .map(subscription -> {
                    binanceService.unsubscribe(principal, symbolName);
                    subscriptionsService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }

    private SubscriptionErrorHandler handleSubscriptionError() {
        return e -> LOGGER.error(e.getMessage(), e);
    }

    private SubscriptionListener<SymbolTickerEvent> emitSymbolTickerEventCallback(Principal principal) {
        return symbolTickerEvent -> {
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
