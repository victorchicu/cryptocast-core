package com.trader.core.clients.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.market.TickerPrice;
import com.trader.core.clients.ApiRestClient;
import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.AssetBalance;
import com.trader.core.exceptions.AssetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExtendedBinanceApiRestClient implements ApiRestClient {
    private static final Logger LOG = LoggerFactory.getLogger(ExtendedBinanceApiRestClient.class);

    private final BinanceProperties binanceProperties;
    private final BinanceApiRestClient binanceApiRestClient;
    private final ConversionService conversionService;

    public ExtendedBinanceApiRestClient(
            BinanceProperties binanceProperties,
            BinanceApiRestClient binanceApiRestClient,
            ConversionService conversionService
    ) {
        this.binanceProperties = binanceProperties;
        this.binanceApiRestClient = binanceApiRestClient;
        this.conversionService = conversionService;
    }

    @Override
    public TickerPrice getPrice(String assetName) {
        String symbolName = getAssetSymbol(assetName);
        try {
            return binanceApiRestClient.getPrice(symbolName);
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
            TickerPrice tickerPrice = new TickerPrice();
            tickerPrice.setSymbol(symbolName);
            tickerPrice.setPrice(BigDecimal.ONE.toPlainString());
            return tickerPrice;
        }
    }

    @Override
    public List<Order> getAllOrders(String assetName, Pageable pageable) {
        String symbolName = getAssetSymbol(assetName);
        return binanceApiRestClient.getAllOrders(new AllOrdersRequest(symbolName));
    }

    @Override
    public List<Order> getOpenOrders(String assetName, Pageable pageable) {
        String symbolName = getAssetSymbol(assetName);
        return binanceApiRestClient.getOpenOrders(new AllOrdersRequest(symbolName));
    }

    @Override
    public List<AssetBalance> getAssetBalances() {
        return binanceApiRestClient.getAccount().getBalances()
                .stream()
                .map(this::toAssetBalance)
                .collect(Collectors.toList());
    }


    private String getAssetSymbol(String assetName) {
        return Optional.ofNullable(binanceProperties.getAssets().get(assetName))
                .map(BinanceProperties.AssetConfig::getSymbol)
                .orElseThrow(() -> new AssetNotFoundException(assetName));
    }

    private AssetBalance toAssetBalance(com.binance.api.client.domain.account.AssetBalance assetBalance) {
        return conversionService.convert(assetBalance, AssetBalance.class);
    }
}
