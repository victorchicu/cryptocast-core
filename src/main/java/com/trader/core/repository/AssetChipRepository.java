package com.trader.core.repository;

import com.trader.core.entity.AssetChipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetChipRepository extends CrudRepository<AssetChipEntity, String> {
    void deleteByAssetNameAndCreatedBy(String assetName, String createdBy);
    List<AssetChipEntity> findAllByCreatedBy(String createdBy);
}
