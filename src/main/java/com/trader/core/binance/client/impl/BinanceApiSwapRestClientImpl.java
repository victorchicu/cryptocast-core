package com.trader.core.binance.client.impl;

import com.trader.core.binance.client.BinanceApiSwapRestClient;
import com.trader.core.binance.client.constant.BinanceApiConstants;
import com.trader.core.binance.client.domain.SwapRemoveType;
import com.trader.core.binance.client.domain.account.*;

import java.util.List;

import static com.trader.core.binance.client.impl.BinanceApiServiceGenerator.createService;

/**
 * Implementation of Binance's SWAP REST API using Retrofit method calls.
 */
public class BinanceApiSwapRestClientImpl implements BinanceApiSwapRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiSwapRestClientImpl(String apiKey, String secret) {
        binanceApiService = BinanceApiServiceGenerator.createService(BinanceApiService.class, apiKey, secret);
    }

    @Override
    public List<Pool> listAllSwapPools() {
        return BinanceApiServiceGenerator.executeSync(binanceApiService.listAllSwapPools());
    }

    @Override
    public Liquidity getPoolLiquidityInfo(String poolId) {
        long timestamp = System.currentTimeMillis();
        List<Liquidity> liquidities = BinanceApiServiceGenerator.executeSync(binanceApiService.getPoolLiquidityInfo(poolId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
        if (liquidities != null && !liquidities.isEmpty()) {
            return liquidities.get(0);
        }
        return null;
    }

    @Override
    public LiquidityOperationRecord addLiquidity(String poolId, String asset, String quantity) {
        long timestamp = System.currentTimeMillis();
        return BinanceApiServiceGenerator.executeSync(binanceApiService.addLiquidity(poolId,
                asset,
                quantity,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public LiquidityOperationRecord removeLiquidity(String poolId, SwapRemoveType type, List<String> asset, String shareAmount) {
        long timestamp = System.currentTimeMillis();
        return BinanceApiServiceGenerator.executeSync(binanceApiService.removeLiquidity(poolId,
                type,
                asset,
                shareAmount,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public List<LiquidityOperationRecord> getPoolLiquidityOperationRecords(String poolId, Integer limit) {
        long timestamp = System.currentTimeMillis();
        return BinanceApiServiceGenerator.executeSync(binanceApiService.getPoolLiquidityOperationRecords(
                poolId,
                limit,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));

    }

    @Override
    public LiquidityOperationRecord getLiquidityOperationRecord(String operationId) {
        long timestamp = System.currentTimeMillis();
        List<LiquidityOperationRecord> records = BinanceApiServiceGenerator.executeSync(binanceApiService.getLiquidityOperationRecord(
                operationId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
        if (records != null && !records.isEmpty()) {
            return records.get(0);
        }
        return null;
    }

    @Override
    public SwapQuote requestQuote(String quoteAsset,
                                  String baseAsset,
                                  String quoteQty) {
        long timestamp = System.currentTimeMillis();
        return BinanceApiServiceGenerator.executeSync(binanceApiService.requestQuote(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public SwapRecord swap(String quoteAsset, String baseAsset, String quoteQty) {
        long timestamp = System.currentTimeMillis();
        return BinanceApiServiceGenerator.executeSync(binanceApiService.swap(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public SwapHistory getSwapHistory(String swapId) {
        long timestamp = System.currentTimeMillis();
        List<SwapHistory> history = BinanceApiServiceGenerator.executeSync(binanceApiService.getSwapHistory(swapId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
        if (history != null && !history.isEmpty()) {
            return history.get(0);
        }
        return null;
    }
}
