package com.crypto.core.utils.stubs;

import com.alibaba.fastjson.JSONObject;
import com.crypto.core.binance.client.SyncRequestClient;
import com.crypto.core.binance.client.domain.ResponseResult;
import com.crypto.core.binance.client.domain.enums.*;
import com.crypto.core.binance.client.domain.market.*;
import com.crypto.core.binance.client.domain.trade.*;

import java.util.List;

public class SyncRequestClientStub implements SyncRequestClient {
    @Override
    public ExchangeInformation getExchangeInformation() {
        return null;
    }

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return null;
    }

    @Override
    public List<Trade> getRecentTrades(String symbol, Integer limit) {
        return null;
    }

    @Override
    public List<Trade> getOldTrades(String symbol, Integer limit, Long fromId) {
        return null;
    }

    @Override
    public List<AggregateTrade> getAggregateTrades(String symbol, Long fromId, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<Candlestick> getCandlestick(String symbol, CandlestickInterval interval, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<MarkPrice> getMarkPrice(String symbol) {
        return null;
    }

    @Override
    public List<FundingRate> getFundingRate(String symbol, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<PriceChangeTicker> get24hrTickerPriceChange(String symbol) {
        return null;
    }

    @Override
    public List<SymbolPrice> getSymbolPriceTicker(String symbol) {
        return null;
    }

    @Override
    public List<SymbolOrderBook> getSymbolOrderBookTicker(String symbol) {
        return null;
    }

    @Override
    public List<LiquidationOrder> getLiquidationOrders(String symbol, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<Object> postBatchOrders(String batchOrders) {
        return null;
    }

    @Override
    public Order postOrder(String symbol, OrderSide side, PositionSide positionSide, OrderType orderType, TimeInForce timeInForce, String quantity, String price, String reduceOnly, String newClientOrderId, String stopPrice, WorkingType workingType, NewOrderRespType newOrderRespType) {
        return null;
    }

    @Override
    public Order cancelOrder(String symbol, Long orderId, String origClientOrderId) {
        return null;
    }

    @Override
    public ResponseResult cancelAllOpenOrder(String symbol) {
        return null;
    }

    @Override
    public List<Object> batchCancelOrders(String symbol, String orderIdList, String origClientOrderIdList) {
        return null;
    }

    @Override
    public ResponseResult changePositionSide(boolean dual) {
        return null;
    }

    @Override
    public ResponseResult changeMarginType(String symbolName, String marginType) {
        return null;
    }

    @Override
    public JSONObject addIsolatedPositionMargin(String symbolName, int type, String amount, PositionSide positionSide) {
        return null;
    }

    @Override
    public List<WalletDeltaLog> getPositionMarginHistory(String symbolName, int type, long startTime, long endTime, int limit) {
        return null;
    }

    @Override
    public JSONObject getPositionSide() {
        return null;
    }

    @Override
    public Order getOrder(String symbol, Long orderId, String origClientOrderId) {
        return null;
    }

    @Override
    public List<Order> getOpenOrders(String symbol) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(String symbol, Long orderId, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<AccountBalance> getBalance() {
        return null;
    }

    @Override
    public AccountInformation getAccountInformation() {
        return null;
    }

    @Override
    public Leverage changeInitialLeverage(String symbol, Integer leverage) {
        return null;
    }

    @Override
    public List<PositionRisk> getPositionRisk() {
        return null;
    }

    @Override
    public List<MyTrade> getAccountTrades(String symbol, Long startTime, Long endTime, Long fromId, Integer limit) {
        return null;
    }

    @Override
    public List<Income> getIncomeHistory(String symbol, IncomeType incomeType, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public String startUserDataStream() {
        return null;
    }

    @Override
    public String keepUserDataStream(String listenKey) {
        return null;
    }

    @Override
    public String closeUserDataStream(String listenKey) {
        return null;
    }

    @Override
    public List<OpenInterestStat> getOpenInterestStat(String symbol, PeriodType period, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<CommonLongShortRatio> getTopTraderAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<CommonLongShortRatio> getTopTraderPositionRatio(String symbol, PeriodType period, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<CommonLongShortRatio> getGlobalAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Integer limit) {
        return null;
    }

    @Override
    public List<TakerLongShortStat> getTakerLongShortRatio(String symbol, PeriodType period, Long startTime, Long endTime, Integer limit) {
        return null;
    }
}
