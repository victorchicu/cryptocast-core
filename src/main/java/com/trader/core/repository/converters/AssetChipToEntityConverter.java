package com.trader.core.repository.converters;

import com.trader.core.domain.AssetChip;
import com.trader.core.entity.AssetChipEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssetChipToEntityConverter implements Converter<AssetChip, AssetChipEntity> {
    @Override
    public AssetChipEntity convert(AssetChip source) {
        AssetChipEntity assetChipEntity = new AssetChipEntity();
        assetChipEntity.setAssetName(source.getAssetName());
        return assetChipEntity;
    }
}
