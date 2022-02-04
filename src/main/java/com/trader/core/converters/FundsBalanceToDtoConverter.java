package com.trader.core.converters;

import com.trader.core.configs.BinanceProperties;
import com.trader.core.domain.FundsBalance;
import com.trader.core.dto.FundsBalanceDto;
import com.trader.core.exceptions.FundsNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FundsBalanceToDtoConverter implements Converter<FundsBalance, FundsBalanceDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FundsBalanceToDtoConverter.class);

    private final BinanceProperties binanceProperties;

    public FundsBalanceToDtoConverter(@Lazy BinanceProperties binanceProperties) {
        this.binanceProperties = binanceProperties;
    }

    @Override
    public FundsBalanceDto convert(FundsBalance source) {
        BinanceProperties.FundsConfig fundsConfig = findFundsByName(source.getAsset());
        return new FundsBalanceDto(
                source.getAsset(),
                fundsConfig.getFullName(),
                fundsConfig.getIcon(),
                source.getFlagged(),
                source.getFree(),
                source.getLocked(),
                source.getPrice(),
                source.getBalance(),
                source.getQuotation()
        );
    }

    private BinanceProperties.FundsConfig findFundsByName(String fundsName) {
        return Optional.ofNullable(binanceProperties.getFunds().get(fundsName))
                .orElseThrow(() -> new FundsNotFoundException(fundsName));
    }
}
