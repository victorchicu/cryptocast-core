package com.coinbank.core.repository.converters;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.entity.AssetTrackerEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToEntityConverter implements Converter<AssetTracker, AssetTrackerEntity> {
    @Override
    public AssetTrackerEntity convert(AssetTracker source) {
        return AssetTrackerEntity.newBuilder()
                .symbolName(source.getAssetName())
                .build();
    }
}
