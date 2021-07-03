<b>${symbol}</b>
<u>Open</u> ${open}
<u>High</u> ${high}
<u>Low</u> ${low}
<u>Close</u> ${lastPrice}

<b>OHLC statistics (24h)</b>
<#if (priceChange > 0)>
    <#lt>${symbol} is up ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.toPlainString(priceChange)}
<#else>
    <#lt>${symbol} is down ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.toPlainString(priceChange)}
</#if>
