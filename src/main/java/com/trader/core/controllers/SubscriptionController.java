package com.trader.core.controllers;

import com.trader.core.domain.Subscription;
import com.trader.core.dto.SubscriptionDto;
import com.trader.core.exceptions.SubscriptionNotFoundException;
import com.trader.core.exceptions.UserNotFoundException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.ExchangeStrategy;
import com.trader.core.services.SubscriptionService;
import com.trader.core.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final UserService userService;
    private final ExchangeStrategy exchangeStrategy;
    private final ConversionService conversionService;
    private final SubscriptionService subscriptionService;

    public SubscriptionController(
            UserService userService,
            ExchangeStrategy exchangeStrategy,
            ConversionService conversionService,
            SubscriptionService subscriptionService
    ) {
        this.userService = userService;
        this.exchangeStrategy = exchangeStrategy;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{assetName}")
    public SubscriptionDto getSubscription(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user -> {
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    return subscriptionService.findSubscription(user, assetName)
                            .map(subscription -> {
                                exchangeService.removeAssetTicker(subscription.getAssetName());
                                exchangeService.createAssetTicker(user, subscription.getAssetName());
                                return subscription;
                            })
                            .map(this::toSubscriptionDto)
                            .orElseThrow(SubscriptionNotFoundException::new);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return userService.findById(principal.getName())
                .map(user -> {
                    SubscriptionDto subscription = subscriptionService.findSubscription(user, assetName)
                            .map(this::toSubscriptionDto)
                            .orElseGet(() -> toSubscriptionDto(
                                    subscriptionService.saveSubscription(
                                            Subscription.newBuilder()
                                                    .assetName(assetName)
                                                    .build()
                                    ))
                            );
                    ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                    exchangeService.createAssetTicker(user, subscription.getAssetName());
                    return subscription;
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping("/{assetName}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSubscription(Principal principal, @PathVariable String assetName) {
        userService.findById(principal.getName()).
                ifPresentOrElse(user -> subscriptionService.findSubscription(user, assetName)
                        .ifPresentOrElse(subscription -> {
                            ExchangeService exchangeService = exchangeStrategy.getExchangeService(user.getExchangeProvider());
                            exchangeService.removeAssetTicker(subscription.getAssetName());
                            subscriptionService.deleteSubscriptionById(subscription.getId());
                        }, () -> new SubscriptionNotFoundException()), () -> new UserNotFoundException()
                );
    }

    @GetMapping
    public Page<SubscriptionDto> listSubscriptions(Principal principal, Pageable pageable) {
        return userService.findById(principal.getName())
                .map(user -> subscriptionService.findSubscriptions(user, pageable)
                        .map(this::toSubscriptionDto)
                )
                .orElseThrow(UserNotFoundException::new);
    }


    private SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return conversionService.convert(subscription, SubscriptionDto.class);
    }
}
