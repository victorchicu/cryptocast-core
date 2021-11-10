package com.trader.core.assets.services.impl;

import com.trader.core.assets.dto.AssetBalanceDto;
import com.trader.core.assets.enums.Quotation;
import com.trader.core.assets.exceptions.AssetNotFoundException;
import com.trader.core.assets.services.AssetService;
import com.trader.core.binance.client.BinanceApiRestClient;
import com.trader.core.binance.client.BinanceApiWebSocketClient;
import com.trader.core.binance.client.domain.account.AssetBalance;
import com.trader.core.binance.client.domain.event.TickerEvent;
import com.trader.core.binance.client.domain.market.TickerPrice;
import com.trader.core.binance.configs.BinanceProperties;
import com.trader.core.notifications.enums.NotificationType;
import com.trader.core.notifications.services.NotificationTemplate;
import com.trader.core.subscriptions.controllers.SubscriptionController;
import com.trader.core.subscriptions.domain.Subscription;
import com.trader.core.subscriptions.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);
    private static final String USDT = "USDT";

    private static final Map<String, Closeable> events = new HashMap<>();

    private final BinanceProperties binanceProperties;
    private final ConversionService conversionService;
    private final SubscriptionService subscriptionService;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiRestClient binanceApiRestClient;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public AssetServiceImpl(
            BinanceProperties binanceProperties,
            ConversionService conversionService,
            SubscriptionService subscriptionService,
            NotificationTemplate notificationTemplate,
            BinanceApiRestClient binanceApiRestClient,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.binanceProperties = binanceProperties;
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiRestClient = binanceApiRestClient;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void addAssetTickerEvent(Principal principal, String assetName) {
        findAssetByName(principal, assetName)
                .map(assetBalance -> {
                    BinanceProperties.AssetConfig assetConfig = findAssetConfigByName(assetName);
                    events.computeIfAbsent(assetName, (String name) ->
                            binanceApiWebSocketClient.onTickerEvent(
                                    assetConfig.getSymbol().toLowerCase(),
                                    tickerEvent -> {
                                        try {
                                            LOG.info(tickerEvent.toString());
                                            sendNotification(principal, assetBalance, tickerEvent);
                                        } catch (Exception e) {
                                            LOG.error(e.getMessage(), e);
                                        }
                                    }
                            ));
                    return assetBalance;
                })
                .orElseThrow(AssetNotFoundException::new);
    }


    @Override
    public void removeAssetTickerEvent(String assetName) {
        Closeable tickerEvent = events.remove(assetName);
        if (tickerEvent != null) {
            try {
                tickerEvent.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<AssetBalance> listAssets(Principal principal) {
        List<AssetBalance> assetBalances = binanceApiRestClient.getAccount().getBalances().stream()
                .filter(this::onlyEffectiveBalance)
                .sorted(Comparator.comparing(AssetBalance::getFree).reversed())
                .map(assetBalance -> {
                    if (assetBalance.getAsset().equals(USDT)) {
                        assetBalance.setPrice(BigDecimal.ONE);
                        assetBalance.setQuotation(Quotation.EQUAL);
                        assetBalance.setBalance(assetBalance.getFree());
                        return assetBalance;
                    } else {
                        BinanceProperties.AssetConfig assetConfig = findAssetConfigByName(assetBalance.getAsset());
                        TickerPrice tickerPrice = binanceApiRestClient.getPrice(assetConfig.getSymbol());
                        return updateAssetBalance(assetBalance, tickerPrice.getPrice());
                    }
                })
                .collect(Collectors.toList());

        Page<Subscription> page = subscriptionService.findSubscriptions(principal, Pageable.unpaged());

        List<Subscription> subscriptions = page.getContent();

        if (subscriptions.size() > 0) {
            assetBalances.forEach(assetBalance -> {
                assetBalance.setFlagged(
                        subscriptions.stream()
                                .anyMatch(subscription -> subscription.getAssetName().equals(assetBalance.getAsset()))
                );
                if (assetBalance.getFlagged()) {
                    removeAssetTickerEvent(assetBalance.getAsset());
                    addAssetTickerEvent(principal, assetBalance.getAsset());
                }
            });
        }

        return assetBalances;
    }

    @Override
    public Optional<AssetBalance> findAssetByName(Principal principal, String assetName) {
        return binanceApiRestClient.getAccount().getBalances().stream()
                .filter(assetBalance -> assetBalance.getAsset().equals(assetName))
                .findFirst();
    }


    private void sendNotification(Principal principal, AssetBalance assetBalance, TickerEvent tickerEvent) {
        AssetBalanceDto assetBalanceDto = toAssetBalanceDto(
                updateAssetBalance(
                        assetBalance,
                        tickerEvent.getCurrentDaysClosePrice()
                )
        );
        notificationTemplate.sendNotification(
                principal,
                NotificationType.TICKER_EVENT,
                assetBalanceDto
        );
    }

    private boolean onlyEffectiveBalance(AssetBalance assetBalance) {
        if (binanceProperties.getBlacklist().contains(assetBalance.getAsset())) {
            return false;
        }

        if (assetBalance.getLocked().compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }

        if (assetBalance.getFree().compareTo(BigDecimal.valueOf(0.00000002)) > 0) {
            return true;
        }

        return false;
    }

    private AssetBalance updateAssetBalance(AssetBalance assetBalance, BigDecimal price) {
        assetBalance.setQuotation(
                assetBalance.getPrice() == null
                        ? Quotation.EQUAL
                        : Quotation.valueOf(price.compareTo(assetBalance.getPrice()))
        );
        assetBalance.setPrice(price);
        BigDecimal balance = BigDecimal.ZERO;
        if (assetBalance.getFree().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    assetBalance.getFree()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        if (assetBalance.getLocked().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    assetBalance.getLocked()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        assetBalance.setBalance(balance);
        return assetBalance;
    }

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }

    private BinanceProperties.AssetConfig findAssetConfigByName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(AssetNotFoundException::new);
    }
}
