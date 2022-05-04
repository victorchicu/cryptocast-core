package com.coinbank.core.repository;

import com.coinbank.core.entity.AssetTrackerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTrackerRepository extends CrudRepository<AssetTrackerEntity, String>, CustomSubscriptionRepository {
}
