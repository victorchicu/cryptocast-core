package com.crypto.core.watchlist.controllers;

import com.crypto.core.binance.client.domain.event.TickerEvent;
import com.crypto.core.binance.client.domain.wallet.Asset;
import com.crypto.core.binance.configs.BinanceProperties;
import com.crypto.core.binance.services.BinanceService;
import com.crypto.core.notifications.enums.NotificationType;
import com.crypto.core.notifications.services.NotificationTemplate;
import com.crypto.core.wallet.dto.AssetDto;
import com.crypto.core.wallet.exceptions.AssetNotFoundException;
import com.crypto.core.wallet.services.WalletService;
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

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchlistController.class);

    private final WalletService walletService;
    private final BinanceService binanceService;
    private final WatchlistService watchlistService;
    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final NotificationTemplate notificationTemplate;

    public WatchlistController(
            WalletService walletService,
            BinanceService binanceService,
            WatchlistService watchlistService,
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            NotificationTemplate notificationTemplate
    ) {
        this.walletService = walletService;
        this.binanceService = binanceService;
        this.conversionService = conversionService;
        this.watchlistService = watchlistService;
        this.binanceProperties = binanceProperties;
        this.notificationTemplate = notificationTemplate;
    }

    @PostMapping("/{assetName}/add")
    public SubscriptionDto addSubscription(Principal principal, @PathVariable String assetName) {
        return walletService.findAssetByName(principal, assetName)
                .map((Asset asset) -> watchlistService.findSubscription(principal, assetName)
                        .map(subscription -> {
                            binanceService.removeTickerEvent(assetName);
                            watchlistService.deleteSubscriptionById(subscription.getId());
                            return toSubscriptionDto(subscription);
                        })
                        .orElseGet(() -> {
                            Subscription subscription = watchlistService.saveSubscription(
                                    Subscription.newBuilder()
                                            .assetName(assetName)
                                            .build()
                            );

                            binanceService.registerTickerEvent(assetName, (TickerEvent tickerEvent) -> {
                                AssetDto assetDto = toAssetDto(asset, tickerEvent);
                                notificationTemplate.sendNotification(principal, NotificationType.TICKER_EVENT, assetDto);
                                LOGGER.info(tickerEvent.toString());
                            });

                            return toSubscriptionDto(subscription);
                        }))
                .orElseThrow(AssetNotFoundException::new);
    }


    @DeleteMapping("/{assetName}/remove")
    public SubscriptionDto removeSubscription(Principal principal, @PathVariable String assetName) {
        return watchlistService.findSubscription(principal, assetName)
                .map(subscription -> {
                    binanceService.removeTickerEvent(assetName);
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

    private AssetDto toAssetDto(Asset asset, TickerEvent tickerEvent) {
        BinanceProperties.Asset internalAsset = getSymbol(asset.getCoin()).orElseThrow(AssetNotFoundException::new);
        return new AssetDto(
                asset.getCoin(),
                asset.getName(),
                internalAsset.getIcon(),
                true,
                asset.getFree(),
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
