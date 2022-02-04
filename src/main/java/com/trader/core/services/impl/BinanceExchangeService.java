package com.trader.core.services.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.clients.ApiWebSocketClient;
import com.trader.core.clients.impl.ExtendedBinanceApiRestClient;
import com.trader.core.clients.impl.ExtendedBinanceApiWebSocketClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.FundsBalance;
import com.trader.core.domain.User;
import com.trader.core.dto.FundsBalanceDto;
import com.trader.core.enums.NotificationType;
import com.trader.core.enums.Quotation;
import com.trader.core.exceptions.FundsNotFoundException;
import com.trader.core.services.ExchangeService;
import com.trader.core.services.NotificationTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service("BINANCE")
public class BinanceExchangeService implements ExchangeService {
    private static final String USDT = "USDT";
    private static final Logger LOG = LoggerFactory.getLogger(BinanceExchangeService.class);
    private static final Map<String, Closeable> events = new HashMap<>();

    private final ApiRestClient apiRestClient;
    private final ApiWebSocketClient apiWebSocketClient;
    private final ConversionService conversionService;
    private final BinanceProperties binanceProperties;
    private final NotificationTemplate notificationTemplate;

    public BinanceExchangeService(
            ApiRestClient apiRestClient,
            ApiWebSocketClient apiWebSocketClient,
            ConversionService conversionService,
            BinanceProperties binanceProperties,
            NotificationTemplate notificationTemplate
    ) {
        this.apiRestClient = apiRestClient;
        this.apiWebSocketClient = apiWebSocketClient;
        this.conversionService = conversionService;
        this.binanceProperties = binanceProperties;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    public void createFundsTicker(User user, String fundsName) {
        findFundsByName(user, fundsName)
                .map(assetBalance -> {
                    events.computeIfAbsent(fundsName, (String name) ->
                            apiWebSocketClient.onTickerEvent(
                                    assetBalance.getAsset(),
                                    tickerEvent -> {
                                        try {
                                            LOG.info(tickerEvent.toString());
                                            sendNotification(user, assetBalance, tickerEvent);
                                        } catch (Exception e) {
                                            LOG.error(e.getMessage(), e);
                                        }
                                    }
                            ));
                    return assetBalance;
                })
                .orElseThrow(() -> new FundsNotFoundException(fundsName));
    }

    @Override
    public void removeFundsTicker(String fundsName) {
        Closeable tickerEvent = events.remove(fundsName);
        if (tickerEvent != null) {
            try {
                tickerEvent.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Set<String> listSymbols() {
        return binanceProperties.getFunds().keySet();
    }

    @Override
    public ApiRestClient newApiRestClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                user.getApiKey(),
                user.getSecretKey(),
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiRestClient(binanceProperties, factory.newRestClient(), conversionService);
    }

    @Override
    public ApiWebSocketClient newApiWebSocketClient(User user) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                user.getApiKey(),
                user.getSecretKey(),
                binanceProperties.getUseTestnet(),
                binanceProperties.getUseTestnetStreaming()
        );
        return new ExtendedBinanceApiWebSocketClient(binanceProperties, factory.newWebSocketClient());
    }

    @Override
    public List<FundsBalance> listFundsBalances(User user) {
        return apiRestClient.getFundsBalances().stream()
                .filter(this::onlyAllowedFunds)
                .filter(this::onlyEffectiveBalance)
                .map((FundsBalance fundsBalance) -> {
                    TickerPrice tickerPrice = apiRestClient.getPrice(fundsBalance.getAsset());
                    return updateFundsBalance(fundsBalance, new BigDecimal(tickerPrice.getPrice()));
                })
                .sorted(Comparator.comparing(FundsBalance::getFree).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FundsBalance> findFundsByName(User user, String fundsName) {
        return apiRestClient.getFundsBalances().stream()
                .filter(assetBalance -> assetBalance.getAsset().equals(fundsName))
                .findFirst();
    }


    private void sendNotification(User user, FundsBalance fundsBalance, TickerEvent tickerEvent) {
        fundsBalance = updateFundsBalance(fundsBalance, new BigDecimal(tickerEvent.getCurrentDaysClosePrice()));
        FundsBalanceDto fundsBalanceDto = toFundsBalanceDto(fundsBalance);
        notificationTemplate.sendNotification(user, NotificationType.TICKER_EVENT, fundsBalanceDto);
    }

    private boolean onlyAllowedFunds(FundsBalance fundsBalance) {
        return !binanceProperties.getBlacklist().contains(fundsBalance.getAsset());
    }

    private boolean onlyEffectiveBalance(FundsBalance fundsBalance) {
        return fundsBalance.getFree().compareTo(BigDecimal.ZERO) > 0 || fundsBalance.getLocked().compareTo(BigDecimal.ZERO) > 0;
    }

    private FundsBalance updateFundsBalance(FundsBalance fundsBalance, BigDecimal price) {
        fundsBalance.setQuotation(
                fundsBalance.getPrice() == null
                        ? Quotation.EQUAL
                        : Quotation.valueOf(price.compareTo(fundsBalance.getPrice()))
        );
        fundsBalance.setPrice(price);
        BigDecimal balance = BigDecimal.ZERO;
        if (fundsBalance.getFree().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    fundsBalance.getFree()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        if (fundsBalance.getLocked().compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(
                    fundsBalance.getLocked()
                            .multiply(price)
                            .setScale(2, RoundingMode.HALF_EVEN)
            );
        }
        fundsBalance.setBalance(balance);
        return fundsBalance;
    }

    private FundsBalanceDto toFundsBalanceDto(FundsBalance fundsBalance) {
        return conversionService.convert(fundsBalance, FundsBalanceDto.class);
    }
}
