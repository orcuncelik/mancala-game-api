package com.celik.mancalaapi.infrastructure.repository;

import com.celik.mancalaapi.infrastructure.repository.entity.MancalaGameStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MancalaGameRepository extends JpaRepository<MancalaGameStateEntity, UUID> {

}
