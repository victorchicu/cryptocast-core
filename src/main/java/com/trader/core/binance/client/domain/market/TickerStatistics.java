package com.trader.core.binance.client.domain.market;

import com.trader.core.binance.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * 24 hour price change statistics for a ticker.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerStatistics {

  /**
   * Ticker symbol.
   */
  private String symbol;

  /**
   * Price change during the last 24 hours.
   */
  private BigDecimal priceChange;

  /**
   * Price change, in percentage, during the last 24 hours.
   */
  private BigDecimal priceChangePercent;

  /**
   * Weighted average price.
   */
  private BigDecimal weightedAvgPrice;

  /**
   * Previous close price.
   */
  private BigDecimal prevClosePrice;

  /**
   * Last price.
   */
  private BigDecimal lastPrice;

  /**
   * Bid price.
   */
  private BigDecimal bidPrice;

  /**
   * Ask price.
   */
  private BigDecimal askPrice;

  /**
   * Open price 24 hours ago.
   */
  private BigDecimal openPrice;

  /**
   * Highest price during the past 24 hours.
   */
  private BigDecimal highPrice;

  /**
   * Lowest price during the past 24 hours.
   */
  private BigDecimal lowPrice;

  /**
   * Total volume during the past 24 hours.
   */
  private BigDecimal volume;

  /**
   * Open time.
   */
  private long openTime;

  /**
   * Close time.
   */
  private long closeTime;

  /**
   * First trade id.
   */
  private long firstId;

  /**
   * Last trade id.
   */
  private long lastId;

  /**
   * Total number of trades during the last 24 hours.
   */
  private long count;

  public BigDecimal getPriceChange() {
    return priceChange;
  }

  public void setPriceChange(BigDecimal priceChange) {
    this.priceChange = priceChange;
  }

  public BigDecimal getPriceChangePercent() {
    return priceChangePercent;
  }

  public void setPriceChangePercent(BigDecimal priceChangePercent) {
    this.priceChangePercent = priceChangePercent;
  }

  public BigDecimal getWeightedAvgPrice() {
    return weightedAvgPrice;
  }

  public void setWeightedAvgPrice(BigDecimal weightedAvgPrice) {
    this.weightedAvgPrice = weightedAvgPrice;
  }

  public BigDecimal getPrevClosePrice() {
    return prevClosePrice;
  }

  public void setPrevClosePrice(BigDecimal prevClosePrice) {
    this.prevClosePrice = prevClosePrice;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(BigDecimal lastPrice) {
    this.lastPrice = lastPrice;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(BigDecimal bidPrice) {
    this.bidPrice = bidPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(BigDecimal askPrice) {
    this.askPrice = askPrice;
  }

  public BigDecimal getOpenPrice() {
    return openPrice;
  }

  public void setOpenPrice(BigDecimal openPrice) {
    this.openPrice = openPrice;
  }

  public BigDecimal getHighPrice() {
    return highPrice;
  }

  public void setHighPrice(BigDecimal highPrice) {
    this.highPrice = highPrice;
  }

  public BigDecimal getLowPrice() {
    return lowPrice;
  }

  public void setLowPrice(BigDecimal lowPrice) {
    this.lowPrice = lowPrice;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(long openTime) {
    this.openTime = openTime;
  }

  public long getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(long closeTime) {
    this.closeTime = closeTime;
  }

  public long getFirstId() {
    return firstId;
  }

  public void setFirstId(long firstId) {
    this.firstId = firstId;
  }

  public long getLastId() {
    return lastId;
  }

  public void setLastId(long lastId) {
    this.lastId = lastId;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public String getSymbol() {
	return symbol;
  }

  public void setSymbol(String symbol) {
	this.symbol = symbol;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("symbol", symbol)
        .append("priceChange", priceChange)
        .append("priceChangePercent", priceChangePercent)
        .append("weightedAvgPrice", weightedAvgPrice)
        .append("prevClosePrice", prevClosePrice)
        .append("lastPrice", lastPrice)
        .append("bidPrice", bidPrice)
        .append("askPrice", askPrice)
        .append("openPrice", openPrice)
        .append("highPrice", highPrice)
        .append("lowPrice", lowPrice)
        .append("volume", volume)
        .append("openTime", openTime)
        .append("closeTime", closeTime)
        .append("firstId", firstId)
        .append("lastId", lastId)
        .append("count", count)
        .toString();
  }
}
