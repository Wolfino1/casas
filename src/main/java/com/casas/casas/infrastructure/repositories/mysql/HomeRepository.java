package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.HomeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeRepository extends JpaRepository<HomeEntity, Long> {
    Optional<HomeEntity> findByName(String name);
    Page<HomeEntity> findAll(Pageable pageable);
}