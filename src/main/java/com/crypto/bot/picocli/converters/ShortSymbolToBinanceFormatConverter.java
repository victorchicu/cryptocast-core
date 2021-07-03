package com.crypto.bot.picocli.converters;

import com.crypto.bot.binance.configs.BinanceProperties;
import com.crypto.bot.exceptions.UnsupportedSymbolNameException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShortSymbolToBinanceFormatConverter implements Converter<String, String> {
    private final BinanceProperties binanceProperties;

    public ShortSymbolToBinanceFormatConverter(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public String convert(String source) {
        return Optional.ofNullable(binanceProperties.getMappings().get(source))
                .map(mappingsConfig -> mappingsConfig.getName())
                .orElseThrow(() -> new UnsupportedSymbolNameException(source));
    }
}
