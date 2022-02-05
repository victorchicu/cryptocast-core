package com.trader.core.controllers;

import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.AssetService;
import com.trader.core.domain.Subscription;
import com.trader.core.dto.SubscriptionDto;
import com.trader.core.exceptions.SubscriptionNotFoundException;
import com.trader.core.services.SubscriptionService;
import com.trader.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final UserService userService;
    private final AssetService assetService;
    private final SubscriptionService subscriptionService;
    private final ConversionService conversionService;


    public SubscriptionController(
            UserService userService,
            AssetService assetService,
            ConversionService conversionService,
            SubscriptionService subscriptionService
    ) {
        this.userService = userService;
        this.assetService = assetService;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user ->
                        subscriptionService.findSubscription(user, assetName)
                                .map(subscription -> {
                                    assetService.removeAssetTickerEvent(user, assetName);
                                    subscriptionService.deleteSubscriptionById(subscription.getId());
                                    return toSubscriptionDto(subscription);
                                }).orElseGet(() -> {
                                    assetService.addAssetTickerEvent(user, assetName);
                                    return toSubscriptionDto(subscriptionService.saveSubscription(
                                            Subscription.newBuilder()
                                                    .assetName(assetName)
                                                    .build()
                                    ));
                                }))
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user ->
                        subscriptionService.findSubscription(user, assetName)
                                .map(subscription -> {
                                    assetService.removeAssetTickerEvent(user, assetName);
                                    subscriptionService.deleteSubscriptionById(subscription.getId());
                                    return toSubscriptionDto(subscription);
                                })
                                .orElseThrow(SubscriptionNotFoundException::new))
                .orElseThrow(UserNotFoundException::new);

    }

    @GetMapping
    public Page<SubscriptionDto> listSubscriptions(Principal principal, Pageable pageable) {
        return userService.findById(principal.getName())
                .map(user ->
                        subscriptionService.findSubscriptions(user, pageable)
                                .map(this::toSubscriptionDto)
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }
}
