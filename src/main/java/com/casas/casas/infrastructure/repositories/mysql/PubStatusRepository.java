package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.PubStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PubStatusRepository extends JpaRepository<PubStatusEntity, String> {
    Optional<PubStatusEntity> findById(Long id);

}
