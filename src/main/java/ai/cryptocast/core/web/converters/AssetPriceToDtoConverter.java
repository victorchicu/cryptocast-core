package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.AssetPrice;
import ai.cryptocast.core.web.dto.AssetPriceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetPriceToDtoConverter implements Converter<AssetPrice, AssetPriceDto> {
    @Override
    public AssetPriceDto convert(AssetPrice source) {
        return new AssetPriceDto(source.getSymbol(), source.getPrice());
    }
}
