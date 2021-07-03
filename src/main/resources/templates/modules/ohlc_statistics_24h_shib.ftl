<b>${symbol}</b>
<u>Open</u> ${NumberUtils.divide(open, 1000)}
<u>High</u> ${NumberUtils.divide(high, 1000)}
<u>Low</u> ${NumberUtils.divide(low, 1000)}
<u>Close</u> ${NumberUtils.divide(lastPrice, 1000)}

<b>OHLC STATISTICS (24h)</b>
<#if (priceChange > 0)>
    <#lt>${symbol} is up ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.divide(priceChange, 1000)}
<#else>
    <#lt>${symbol} is down ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.divide(priceChange, 1000)}
</#if>
