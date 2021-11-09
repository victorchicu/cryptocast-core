package com.trader.core.subscriptions.controllers;

import com.trader.core.binance.assets.services.AssetService;
import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.dto.SubscriptionDto;
import com.trader.core.subscriptions.exceptions.SubscriptionNotFoundException;
import com.trader.core.subscriptions.services.SubscriptionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final AssetService assetService;
    private final SubscriptionService subscriptionService;
    private final ConversionService conversionService;


    public SubscriptionController(
            AssetService assetService,
            ConversionService conversionService,
            SubscriptionService subscriptionService
    ) {
        this.assetService = assetService;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return subscriptionService.findSubscription(principal, assetName)
                .map(subscription -> {
                    assetService.removeAssetTickerEvent(assetName);
                    subscriptionService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                }).orElseGet(() -> {
                    assetService.addAssetTickerEvent(principal, assetName);
                    return toSubscriptionDto(subscriptionService.saveSubscription(
                            Subscription.newBuilder()
                                    .assetName(assetName)
                                    .build()
                    ));
                });
    }

    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return subscriptionService.findSubscription(principal, assetName)
                .map(subscription -> {
                    assetService.removeAssetTickerEvent(assetName);
                    subscriptionService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                })
                .orElseThrow(SubscriptionNotFoundException::new);
    }

    @GetMapping
    public Page<SubscriptionDto> listSubscriptions(Principal principal, Pageable pageable) {
        return subscriptionService.findSubscriptions(principal, pageable)
                .map(subscription -> {
                    assetService.addAssetTickerEvent(principal, subscription.getAssetName());
                    return toSubscriptionDto(subscription);
                });
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }
}
