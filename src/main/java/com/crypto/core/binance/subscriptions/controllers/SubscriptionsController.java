package com.crypto.core.binance.subscriptions.controllers;

import com.crypto.core.binance.client.SubscriptionErrorHandler;
import com.crypto.core.binance.client.SubscriptionListener;
import com.crypto.core.binance.client.domain.event.SymbolTickerEvent;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.notifications.enums.EventSupport;
import com.crypto.core.notifications.services.NotificationEmitter;
import com.crypto.core.binance.subscriptions.domain.Subscription;
import com.crypto.core.binance.subscriptions.dto.SubscriptionDto;
import com.crypto.core.binance.subscriptions.services.SubscriptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsController.class);

    private final BinanceService binanceService;
    private final ConversionService conversionService;
    private final NotificationEmitter notificationEmitter;
    private final SubscriptionsService subscriptionsService;

    public SubscriptionsController(
            BinanceService binanceService,
            ConversionService conversionService,
            NotificationEmitter notificationEmitter,
            SubscriptionsService subscriptionsService
    ) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.notificationEmitter = notificationEmitter;
        this.subscriptionsService = subscriptionsService;
    }

    @PutMapping("/{symbolName}/subscribe")
    public SubscriptionDto subscribe(Principal principal, @PathVariable String symbolName) {
        return subscriptionsService.findSubscription(principal.getName(), symbolName)
                .map(this::toSubscriptionDto)
                .orElseGet(() -> {
                    Subscription subscription = subscriptionsService.saveSubscription(
                            Subscription.newBuilder()
                                    .symbolName(symbolName)
                                    .build()
                    );

                    SubscriptionListener<SymbolTickerEvent> subscriptionListener = (SymbolTickerEvent symbolTickerEvent) -> {
                        notificationEmitter.emit(
                                EventSupport.SYMBOL_OHLC_REPORT,
                                symbolTickerEvent
                        );
                    };

                    SubscriptionErrorHandler subscriptionErrorHandler = e -> LOGGER.error(e.getMessage(), e);

                    binanceService.subscribe(principal.getName(), symbolName, subscriptionListener, subscriptionErrorHandler);

                    return toSubscriptionDto(subscription);
                });
    }

    @DeleteMapping("/{symbolName}/unsubscribe")
    public SubscriptionDto unsubscribe(Principal principal, @PathVariable String symbolName) {
        return subscriptionsService.findSubscription(principal.getName(), symbolName)
                .map(subscription -> {
                    binanceService.unsubscribe(principal.getName(), symbolName);
                    subscriptionsService.deleteSubscription(subscription.getId());
                    return toSubscriptionDto(subscription);
                })
                .orElseThrow(RuntimeException::new);
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }

    private Map<String, Object> toPayload(SymbolTickerEvent symbolTickerEvent) {
        return conversionService.convert(symbolTickerEvent, Map.class);
    }
}
