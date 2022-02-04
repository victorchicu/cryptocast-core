package com.trader.core.controllers;

import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.FundsService;
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
    private final FundsService fundsService;
    private final SubscriptionService subscriptionService;
    private final ConversionService conversionService;


    public SubscriptionController(
            UserService userService,
            FundsService fundsService,
            ConversionService conversionService,
            SubscriptionService subscriptionService
    ) {
        this.userService = userService;
        this.fundsService = fundsService;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{fundsName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String fundsName) {
        return userService.findById(principal.getName())
                .map(user ->
                        subscriptionService.findSubscription(user, fundsName)
                                .map(subscription -> {
                                    fundsService.removeFundsTickerEvent(user, fundsName);
                                    subscriptionService.deleteSubscriptionById(subscription.getId());
                                    return toSubscriptionDto(subscription);
                                }).orElseGet(() -> {
                                    fundsService.addFundsTickerEvent(user, fundsName);
                                    return toSubscriptionDto(subscriptionService.saveSubscription(
                                            Subscription.newBuilder()
                                                    .fundsName(fundsName)
                                                    .build()
                                    ));
                                }))
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping("/{fundsName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String fundsName) {
        //TODO: Changed to find by id
        return userService.findById(principal.getName())
                .map(user ->
                        subscriptionService.findSubscription(user, fundsName)
                                .map(subscription -> {
                                    fundsService.removeFundsTickerEvent(user, fundsName);
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
