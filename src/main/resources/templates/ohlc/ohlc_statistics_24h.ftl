<b>OHLC STATISTICS (24h)</b>

<#if (priceChange > 0)>
    <#lt><b>${assetBalance}</b> is up ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.toPlainString(priceChange)}
<#else>
    <#lt><b>${assetBalance}</b> is down ${NumberUtils.toPlainString(priceChangePercent)}% to ${NumberUtils.toPlainString(priceChange)}
</#if>
<u>🅞</u> ${open} ┃ <u>🅗</u> ${high} ┃ <u>🅛</u> ${low} ┃ <u>🅒</u> ${lastPrice}
