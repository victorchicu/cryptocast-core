package com.crypto.core.picocli.converters;

import com.crypto.core.exchanges.binance.configs.BinanceProperties;
import com.crypto.core.exchanges.binance.exceptions.SymbolNotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SymbolToBinanceSymbolConverter implements Converter<String, String> {
    private final BinanceProperties binanceProperties;

    public SymbolToBinanceSymbolConverter(BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public String convert(String source) {
        return Optional.ofNullable(binanceProperties.getSymbols().get(source))
                .map(mappingsConfig -> mappingsConfig.getName())
                .orElseThrow(SymbolNotFoundException::new);
    }
}
