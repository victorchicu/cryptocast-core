package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.configs.Cryptocurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CryptostropheBotApplicationIT {
    @Autowired
    private BinanceProperties binanceProperties;

    @Test
    public void should_go_or_down() {
        SymbolMiniTickerEvent event = new SymbolMiniTickerEvent();

        event.setSymbol("1000SHIBUSDT");
        event.setOpen(BigDecimal.valueOf(0.000010739));
        event.setClose(BigDecimal.valueOf(0.00001117));
        event.setHigh(BigDecimal.valueOf(0.00001115));
        event.setLow(BigDecimal.valueOf(0.000008705));
        event.setTotalTradedBaseAssetVolume(BigDecimal.valueOf(58725435015.0));
        event.setTotalTradedQuoteAssetVolume(BigDecimal.valueOf(571872987.49));

        if (event.getClose().compareTo(event.getHigh()) > 0) {
            //|(1052−1051)÷[(1052+1051)÷2]|×100
            String str = String.format("%s IS UP\n%s", "1000SHIBUSDT", printSymbolMiniTickerEvent(event));
            System.out.println(str);
        }
    }

    private String printSymbolMiniTickerEvent(SymbolMiniTickerEvent event) {
        Cryptocurrency cryptocurrency = binanceProperties.getCryptocurrency().get(event.getSymbol());
        StringBuilder textBuilder = new StringBuilder();
        if (cryptocurrency.getDivisor() == null) {
            textBuilder = textBuilder.append("Symbol:" + event.getSymbol() + "\n");
            textBuilder = textBuilder.append("Open: " + event.getOpen().toPlainString() + "\n");
            textBuilder = textBuilder.append("Close: " + event.getClose().toPlainString() + "\n");
            textBuilder = textBuilder.append("High: " + event.getHigh().toPlainString() + "\n");
            textBuilder = textBuilder.append("Low: " + event.getLow().toPlainString() + "\n");
        } else {
            textBuilder = textBuilder.append("Symbol: " + event.getSymbol() + "\n");
            textBuilder = textBuilder.append("Open: " + event.getOpen().divide(cryptocurrency.getDivisor()).toPlainString() + "\n");
            textBuilder = textBuilder.append("Close: " + event.getClose().divide(cryptocurrency.getDivisor()).toPlainString() + "\n");
            textBuilder = textBuilder.append("High: " + event.getHigh().divide(cryptocurrency.getDivisor()).toPlainString() + "\n");
            textBuilder = textBuilder.append("Low: " + event.getLow().divide(cryptocurrency.getDivisor()).toPlainString() + "\n");
        }
        textBuilder = textBuilder.append("Total traded base asset volume: " + event.getTotalTradedBaseAssetVolume().toPlainString() + "\n");
        textBuilder = textBuilder.append("Total traded quote asset volume: " + event.getTotalTradedQuoteAssetVolume().toPlainString() + "\n");
        return textBuilder.toString();
    }
}
