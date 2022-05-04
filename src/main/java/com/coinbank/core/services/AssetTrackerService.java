package com.coinbank.core.services;

import com.coinbank.core.domain.AssetTracker;
import com.coinbank.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssetTrackerService {
    void deleteAll(User user);

    void deleteById(String id);

    AssetTracker save(AssetTracker assetTracker);

    Page<AssetTracker> findAll(User user, Pageable pageable);

    Page<AssetTracker> findAll(User user, List<String> assetNames, Pageable pageable);

    Optional<AssetTracker> findByAssetName(User user, String assetName);
}
