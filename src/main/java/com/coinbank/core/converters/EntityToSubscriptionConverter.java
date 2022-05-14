package com.coinbank.core.converters;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.entity.AssetTrackerEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToSubscriptionConverter implements Converter<AssetTrackerEntity, AssetTracker> {
    @Override
    public AssetTracker convert(AssetTrackerEntity source) {
        return AssetTracker.newBuilder()
                .id(source.getId())
                .assetName(source.getAssetName())
                .build();
    }
}
