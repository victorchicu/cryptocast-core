package com.crypto.core.subscriptions.controllers;

import com.crypto.core.binance.client.BinanceApiRestClient;
import com.crypto.core.binance.client.BinanceApiWebSocketClient;
import com.crypto.core.binance.client.domain.account.AssetBalance;
import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.dto.AssetBalanceDto;
import com.crypto.core.binance.exceptions.AssetNotFoundException;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.notifications.enums.NotificationType;
import com.crypto.core.notifications.services.NotificationTemplate;
import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.dto.SubscriptionDto;
import com.crypto.core.subscriptions.exceptions.SubscriptionNotFoundException;
import com.crypto.core.subscriptions.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    private final BinanceService binanceService;
    private final SubscriptionService subscriptionService;
    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;


    public SubscriptionController(
            BinanceService binanceService,
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            SubscriptionService subscriptionService,
            NotificationTemplate notificationTemplate,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.binanceProperties = binanceProperties;
        this.subscriptionService = subscriptionService;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return binanceService.findAssetByName(principal, assetName)
                .map((AssetBalance assetBalance) -> subscriptionService.findSubscription(principal, assetName)
                        .map(subscription -> {
                            binanceService.removeTickerEvent(assetName);
                            subscriptionService.deleteSubscriptionById(subscription.getId());
                            return toSubscriptionDto(subscription);
                        })
                        .orElseGet(() -> {
                            Subscription subscription = subscriptionService.saveSubscription(
                                    Subscription.newBuilder()
                                            .assetName(assetName)
                                            .build()
                            );

                            binanceService.addTickerEvent(assetName, (TickerEvent tickerEvent) -> {
                                AssetBalanceDto assetDto = toAssetBalanceDto(assetBalance, tickerEvent);
                                notificationTemplate.sendNotification(principal, NotificationType.TICKER_EVENT, assetDto);
                                LOGGER.info(tickerEvent.toString());
                            });

                            return toSubscriptionDto(subscription);
                        }))
                .orElseThrow(AssetNotFoundException::new);
    }

    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return subscriptionService.findSubscription(principal, assetName)
                .map(subscription -> {
                    binanceService.removeTickerEvent(assetName);
                    subscriptionService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }

    @GetMapping
    public Page<SubscriptionDto> listSubscriptions(Principal principal, Pageable pageable) {
        return subscriptionService.findSubscriptions(principal, pageable)
                .map(this::toSubscriptionDto);
    }


    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance, TickerEvent tickerEvent) {
        BinanceProperties.Asset internalAsset = getSymbol(assetBalance.getAsset()).orElseThrow(AssetNotFoundException::new);
        return new AssetBalanceDto(
                assetBalance.getAsset(),
                assetBalance.getName(),
                internalAsset.getIcon(),
                true,
                assetBalance.getFree(),
                new BigDecimal(tickerEvent.getOpenPrice()),
                new BigDecimal(tickerEvent.getHighPrice()),
                new BigDecimal(tickerEvent.getLowPrice()),
                new BigDecimal(tickerEvent.getWeightedAveragePrice())
        );
    }

    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }

    private Optional<BinanceProperties.Asset> getSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName));
    }
}
