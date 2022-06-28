<b>OHLC STATISTICS (24h)</b>

<#if (priceChange > 0)>
    <#lt><b>${assetBalance}</b> is up ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.divide(priceChange, 1000)}
<#else>
    <#lt><b>${assetBalance}</b> is down ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.divide(priceChange, 1000)}
</#if>
<u>🅞</u> ${NumberUtils.divide(open, 1000)} ┃ <u>🅗</u> ${NumberUtils.divide(high, 1000)} ┃ <u>🅛</u> ${NumberUtils.divide(low, 1000)} ┃ <u>🅒</u> ${NumberUtils.divide(lastPrice, 1000)}
