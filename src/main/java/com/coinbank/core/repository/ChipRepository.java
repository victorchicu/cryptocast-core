package com.coinbank.core.repository;

import com.coinbank.core.repository.entity.ChipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipRepository extends CrudRepository<ChipEntity, String> {
    void deleteByNameAndCreatedBy(String name, String createdBy);
    List<ChipEntity> findAllByCreatedBy(String createdBy);
}
