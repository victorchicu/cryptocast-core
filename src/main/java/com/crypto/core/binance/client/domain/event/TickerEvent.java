package com.crypto.core.binance.client.domain.event;

import com.crypto.core.binance.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerEvent {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("p")
    private BigDecimal priceChange;

    @JsonProperty("P")
    private BigDecimal priceChangePercent;

    @JsonProperty("w")
    private BigDecimal weightedAveragePrice;

    @JsonProperty("x")
    private BigDecimal previousDaysClosePrice;

    @JsonProperty("c")
    private BigDecimal currentDaysClosePrice;

    @JsonProperty("Q")
    private BigDecimal closeTradesQuantity;

    @JsonProperty("b")
    private BigDecimal bestBidPrice;

    @JsonProperty("B")
    private BigDecimal bestBidQuantity;

    @JsonProperty("a")
    private BigDecimal bestAskPrice;

    @JsonProperty("A")
    private BigDecimal bestAskQuantity;

    @JsonProperty("o")
    private BigDecimal openPrice;

    @JsonProperty("h")
    private BigDecimal highPrice;

    @JsonProperty("l")
    private BigDecimal lowPrice;

    @JsonProperty("v")
    private BigDecimal totalTradedBaseAssetVolume;

    @JsonProperty("q")
    private BigDecimal totalTradedQuoteAssetVolume;

    @JsonProperty("O")
    private long statisticsOpenTime;

    @JsonProperty("C")
    private long statisticsCloseTime;

    @JsonProperty("F")
    private long firstTradeId;

    @JsonProperty("L")
    private long lastTradeId;

    @JsonProperty("n")
    private long totalNumberOfTrades;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

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

    public BigDecimal getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    public void setWeightedAveragePrice(BigDecimal weightedAveragePrice) {
        this.weightedAveragePrice = weightedAveragePrice;
    }

    public BigDecimal getPreviousDaysClosePrice() {
        return previousDaysClosePrice;
    }

    public void setPreviousDaysClosePrice(BigDecimal previousDaysClosePrice) {
        this.previousDaysClosePrice = previousDaysClosePrice;
    }

    public BigDecimal getCurrentDaysClosePrice() {
        return currentDaysClosePrice;
    }

    public void setCurrentDaysClosePrice(BigDecimal currentDaysClosePrice) {
        this.currentDaysClosePrice = currentDaysClosePrice;
    }

    public BigDecimal getCloseTradesQuantity() {
        return closeTradesQuantity;
    }

    public void setCloseTradesQuantity(BigDecimal closeTradesQuantity) {
        this.closeTradesQuantity = closeTradesQuantity;
    }

    public BigDecimal getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(BigDecimal bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public BigDecimal getBestBidQuantity() {
        return bestBidQuantity;
    }

    public void setBestBidQuantity(BigDecimal bestBidQuantity) {
        this.bestBidQuantity = bestBidQuantity;
    }

    public BigDecimal getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(BigDecimal bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public BigDecimal getBestAskQuantity() {
        return bestAskQuantity;
    }

    public void setBestAskQuantity(BigDecimal bestAskQuantity) {
        this.bestAskQuantity = bestAskQuantity;
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

    public BigDecimal getTotalTradedBaseAssetVolume() {
        return totalTradedBaseAssetVolume;
    }

    public void setTotalTradedBaseAssetVolume(BigDecimal totalTradedBaseAssetVolume) {
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
    }

    public BigDecimal getTotalTradedQuoteAssetVolume() {
        return totalTradedQuoteAssetVolume;
    }

    public void setTotalTradedQuoteAssetVolume(BigDecimal totalTradedQuoteAssetVolume) {
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    public long getStatisticsOpenTime() {
        return statisticsOpenTime;
    }

    public void setStatisticsOpenTime(long statisticsOpenTime) {
        this.statisticsOpenTime = statisticsOpenTime;
    }

    public long getStatisticsCloseTime() {
        return statisticsCloseTime;
    }

    public void setStatisticsCloseTime(long statisticsCloseTime) {
        this.statisticsCloseTime = statisticsCloseTime;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public void setFirstTradeId(long firstTradeId) {
        this.firstTradeId = firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public void setLastTradeId(long lastTradeId) {
        this.lastTradeId = lastTradeId;
    }

    public long getTotalNumberOfTrades() {
        return totalNumberOfTrades;
    }

    public void setTotalNumberOfTrades(long totalNumberOfTrades) {
        this.totalNumberOfTrades = totalNumberOfTrades;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("eventType", eventType)
                .append("eventTime", eventTime)
                .append("symbol", symbol)
                .append("priceChange", priceChange)
                .append("priceChangePercent", priceChangePercent)
                .append("weightedAveragePrice", weightedAveragePrice)
                .append("previousDaysClosePrice", previousDaysClosePrice)
                .append("currentDaysClosePrice", currentDaysClosePrice)
                .append("closeTradesQuantity", closeTradesQuantity)
                .append("bestBidPrice", bestBidPrice)
                .append("bestBidQuantity", bestBidQuantity)
                .append("bestAskPrice", bestAskPrice)
                .append("bestAskQuantity", bestAskQuantity)
                .append("openPrice", openPrice)
                .append("highPrice", highPrice)
                .append("lowPrice", lowPrice)
                .append("totalTradedBaseAssetVolume", totalTradedBaseAssetVolume)
                .append("totalTradedQuoteAssetVolume", totalTradedQuoteAssetVolume)
                .append("statisticsOpenTime", statisticsOpenTime)
                .append("statisticsCloseTime", statisticsCloseTime)
                .append("firstTradeId", firstTradeId)
                .append("lastTradeId", lastTradeId)
                .append("totalNumberOfTrades", totalNumberOfTrades)
                .toString();
    }
}
