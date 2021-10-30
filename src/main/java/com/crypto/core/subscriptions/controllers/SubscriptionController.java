package com.crypto.core.subscriptions.controllers;

import com.crypto.core.binance.assets.services.AssetService;
import com.crypto.core.subscriptions.domain.Subscription;
import com.crypto.core.subscriptions.dto.SubscriptionDto;
import com.crypto.core.subscriptions.exceptions.SubscriptionNotFoundException;
import com.crypto.core.subscriptions.services.SubscriptionService;
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
                    assetService.removeTickerEvent(assetName);
                    subscriptionService.deleteSubscriptionById(subscription.getId());
                    return toSubscriptionDto(subscription);
                }).orElseGet(() -> {
                    Subscription subscription = subscriptionService.saveSubscription(Subscription.newBuilder().assetName(assetName).build());
                    assetService.addTickerEvent(principal, assetName);
                    return toSubscriptionDto(subscription);
                });
    }

    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return subscriptionService.findSubscription(principal, assetName)
                .map(subscription -> {
                    assetService.removeTickerEvent(assetName);
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

    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }


}
