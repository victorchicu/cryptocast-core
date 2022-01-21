package com.trader.core.binance.impl;

import com.trader.core.binance.BinanceApiRestClient;
import com.trader.core.binance.config.BinanceApiConfig;
import com.trader.core.binance.constant.BinanceApiConstants;
import com.trader.core.binance.domain.account.*;
import com.trader.core.binance.domain.account.request.*;
import com.trader.core.binance.domain.general.BinanceResponse;
import com.trader.core.binance.domain.general.ExchangeInfo;
import com.trader.core.binance.domain.general.UserAsset;
import com.trader.core.binance.domain.market.*;
import com.trader.core.binance.domain.wallet.Asset;
import com.trader.core.binance.exception.BinanceApiException;
import retrofit2.Call;

import java.util.List;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking
 * method calls.
 */
public class BinanceApiRestClientImpl implements BinanceApiRestClient {

	private final BinanceApiService binanceApiService;

	public BinanceApiRestClientImpl(String apiKey, String secret) {
		binanceApiService = BinanceApiServiceGenerator.createService(BinanceApiService.class, apiKey, secret);
	}

	// General endpoints

	@Override
	public void ping() {
		BinanceApiServiceGenerator.executeSync(binanceApiService.ping());
	}

	@Override
	public Long getServerTime() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getServerTime()).getServerTime();
	}

	@Override
	public ExchangeInfo getExchangeInfo() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getExchangeInfo());
	}

	@Override
	public List<UserAsset> getUserAssets(boolean needBtcValuation) {
		String url = BinanceApiConfig.getAssetInfoApiBaseUrl() + "/bapi/asset/v3/private/asset-service/asset/get-user-asset";
		BinanceResponse<List<UserAsset>> binanceResponse = BinanceApiServiceGenerator.executeSync(binanceApiService.getUserAssets(url, needBtcValuation));
		if (!binanceResponse.isSuccess()) {
			throw new BinanceApiException(binanceResponse.getMessage());
		}
		return binanceResponse.getData();
	}

	@Override
	public List<Asset> getAllAssets() {
		String url = BinanceApiConfig.getAssetInfoApiBaseUrl() + "/bapi/asset/v2/public/asset/asset/get-all-asset";
		BinanceResponse<List<Asset>> binanceResponse = BinanceApiServiceGenerator.executeSync(binanceApiService.getAllAssets(url));
		if (!binanceResponse.isSuccess()) {
			throw new BinanceApiException(binanceResponse.getMessage());
		}
		return binanceResponse.getData();
	}

	// Market Data endpoints

	@Override
	public OrderBook getOrderBook(String symbol, Integer limit) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getOrderBook(symbol, limit));
	}

	@Override
	public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getTrades(symbol, limit));
	}

	@Override
	public List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getHistoricalTrades(symbol, limit, fromId));
	}

	@Override
	public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
	}

	@Override
	public List<AggTrade> getAggTrades(String symbol) {
		return getAggTrades(symbol, null, null, null, null);
	}

	@Override
	public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit,
                                                Long startTime, Long endTime) {
		return BinanceApiServiceGenerator.executeSync(
				binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
	}

	@Override
	public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
		return getCandlestickBars(symbol, interval, null, null, null);
	}

	@Override
	public TickerStatistics get24HrPriceStatistics(String symbol) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.get24HrPriceStatistics(symbol));
	}

	@Override
	public List<TickerStatistics> getAll24HrPriceStatistics() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getAll24HrPriceStatistics());
	}

	@Override
	public TickerPrice getPrice(String symbol) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getLatestPrice(symbol));
	}

	@Override
	public List<TickerPrice> getAllPrices() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getLatestPrices());
	}

	@Override
	public List<BookTicker> getBookTickers() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getBookTickers());
	}

	@Override
	public NewOrderResponse newOrder(NewOrder order) {
		final Call<NewOrderResponse> call;
		if (order.getQuoteOrderQty() == null) {
			call = binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
					order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
					order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
					order.getTimestamp());
		} else {
			call = binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
					order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(), order.getNewClientOrderId(),
					order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
					order.getTimestamp());
		}
		return BinanceApiServiceGenerator.executeSync(call);
	}

	@Override
	public void newOrderTest(NewOrder order) {
		BinanceApiServiceGenerator.executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
				order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
				order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
				order.getTimestamp()));
	}

	// Account endpoints

	@Override
	public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
				orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
				orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
	}

	@Override
	public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
		return BinanceApiServiceGenerator.executeSync(
				binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(), cancelOrderRequest.getOrderId(),
						cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
						cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
	}

	@Override
	public List<Order> getOpenOrders(OrderRequest orderRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(),
				orderRequest.getTimestamp()));
	}

	@Override
	public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(), orderRequest.getOrderId(),
				orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
	}

	@Override
	public NewOCOResponse newOCO(NewOCO oco) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.newOCO(oco.getSymbol(), oco.getListClientOrderId(), oco.getSide(),
				oco.getQuantity(), oco.getLimitClientOrderId(), oco.getPrice(), oco.getLimitIcebergQty(),
				oco.getStopClientOrderId(), oco.getStopPrice(), oco.getStopLimitPrice(), oco.getStopIcebergQty(),
				oco.getStopLimitTimeInForce(), oco.getNewOrderRespType(), oco.getRecvWindow(), oco.getTimestamp()));
	}

	@Override
	public CancelOrderListResponse cancelOrderList(CancelOrderListRequest cancelOrderListRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.cancelOrderList(cancelOrderListRequest.getSymbol(), cancelOrderListRequest.getOrderListId(),
				cancelOrderListRequest.getListClientOrderId(), cancelOrderListRequest.getNewClientOrderId(),
				cancelOrderListRequest.getRecvWindow(), cancelOrderListRequest.getTimestamp()));
	}

	@Override
	public OrderList getOrderListStatus(OrderListStatusRequest orderListStatusRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getOrderListStatus(orderListStatusRequest.getOrderListId(), orderListStatusRequest.getOrigClientOrderId(),
				orderListStatusRequest.getRecvWindow(), orderListStatusRequest.getTimestamp()));
	}

	@Override
	public List<OrderList> getAllOrderList(AllOrderListRequest allOrderListRequest) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getAllOrderList(allOrderListRequest.getFromId(), allOrderListRequest.getStartTime(),
				allOrderListRequest.getEndTime(), allOrderListRequest.getLimit(), allOrderListRequest.getRecvWindow(), allOrderListRequest.getTimestamp()));
	}

	@Override
	public Account getAccount(Long recvWindow, Long timestamp) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getAccount(recvWindow, timestamp));
	}

	@Override
	public Account getAccount() {
		return getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp));
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Integer limit) {
		return getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol) {
		return getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public List<Trade> getMyTrades(String symbol, Long fromId) {
		return getMyTrades(symbol, null, fromId, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis());
	}

	@Override
	public WithdrawResult withdraw(String asset, String address, String amount, String name, String addressTag) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.withdraw(asset, address, amount, name, addressTag,
				BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
	}

	@Override
	public DustTransferResponse dustTranfer(List<String> asset) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.dustTransfer(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
	}

	@Override
	public DepositHistory getDepositHistory(String asset) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	@Override
	public WithdrawHistory getWithdrawHistory(String asset) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	@Override
	public List<SubAccountTransfer> getSubAccountTransfers() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getSubAccountTransfers(System.currentTimeMillis()));
	}

	@Override
	public DepositAddress getDepositAddress(String asset) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
				System.currentTimeMillis()));
	}

	// User stream endpoints

	@Override
	public String startUserDataStream() {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.startUserDataStream()).toString();
	}

	@Override
	public void keepAliveUserDataStream(String listenKey) {
		BinanceApiServiceGenerator.executeSync(binanceApiService.keepAliveUserDataStream(listenKey));
	}

	@Override
	public void closeUserDataStream(String listenKey) {
		BinanceApiServiceGenerator.executeSync(binanceApiService.closeAliveUserDataStream(listenKey));
	}

	//Wallet endpoints

	@Override
	public List<Asset> listAssets(Long recvWindow, Long timestamp) {
		return BinanceApiServiceGenerator.executeSync(binanceApiService.listAssets(recvWindow, timestamp));
	}
}
