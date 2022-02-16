package com.trader.core.converters;

import com.trader.core.domain.AssetPrice;
import com.trader.core.dto.AssetPriceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetPriceToDtoConverter implements Converter<AssetPrice, AssetPriceDto> {
    @Override
    public AssetPriceDto convert(AssetPrice source) {
        return new AssetPriceDto(source.getSymbol(), source.getPrice());
    }
}
