package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByCityAndDepartment(String city, String department);
    Page<LocationEntity> findByCityContainingAndDepartmentContaining(String city, String department, Pageable pageable);
    Page<LocationEntity> findByCityContaining(String city, Pageable pageable);
    Page<LocationEntity> findByDepartmentContaining(String department, Pageable pageable);
}