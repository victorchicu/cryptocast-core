package com.trader.core.services.impl;

import com.trader.core.dto.AssetBalanceDto;
import com.trader.core.enums.Quotation;
import com.trader.core.exceptions.AssetNotFoundException;
import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.BinanceApiWebSocketClient;
import com.trader.core.binance.domain.account.AssetBalance;
import com.trader.core.binance.domain.event.TickerEvent;
import com.trader.core.binance.domain.market.TickerPrice;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.services.ExchangeProviderService;
import com.trader.core.enums.NotificationType;
import com.trader.core.services.NotificationTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service("BINANCE")
public class BinanceExchangeProviderService implements ExchangeProviderService {
    private static final String USDT = "USDT";
    private static final Logger LOG = LoggerFactory.getLogger(BinanceExchangeProviderService.class);
    private static final Map<String, Closeable> events = new HashMap<>();

    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final NotificationTemplate notificationTemplate;
    private final BinanceApiWebSocketClient binanceApiWebSocketClient;

    public BinanceExchangeProviderService(
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            NotificationTemplate notificationTemplate,
            BinanceApiWebSocketClient binanceApiWebSocketClient
    ) {
        this.conversionService = conversionService;
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.notificationTemplate = notificationTemplate;
        this.binanceApiWebSocketClient = binanceApiWebSocketClient;
    }

    @Override
    public void createAssetTicker(Principal principal, String assetName) {
        findAssetBalanceByName(principal, assetName)
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
    public void removeAssetTicker(String assetName) {
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
    public List<AssetBalance> listAssetBalances(Principal principal) {
        return binanceApiRestClient.getAccount().getBalances().stream()
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
    }

    @Override
    public Optional<AssetBalance> findAssetBalanceByName(Principal principal, String assetName) {
        return binanceApiRestClient.getAccount().getBalances().stream()
                .filter(assetBalance -> assetBalance.getAsset().equals(assetName))
                .findFirst();
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

    private BinanceProperties.AssetConfig findAssetConfigByName(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .orElseThrow(AssetNotFoundException::new);
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

    private AssetBalanceDto toAssetBalanceDto(AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalanceDto.class);
    }
}
