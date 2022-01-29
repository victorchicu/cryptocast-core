package com.trader.core.repository;

import com.trader.core.entity.ChipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipRepository extends CrudRepository<ChipEntity, String> {
    void deleteByNameAndCreatedBy(String name, String createdBy);
    List<ChipEntity> findAllByCreatedBy(String createdBy);
}
