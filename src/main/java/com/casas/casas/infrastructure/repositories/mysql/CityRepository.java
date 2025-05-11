package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByName(String name);
    List<CityEntity> findByDepartmentId(Long departmentId);


}
