package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    Optional<DepartmentEntity> findByName(String name);
}
