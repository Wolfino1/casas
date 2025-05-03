package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.DepartmentModel;

import java.util.List;
import java.util.Optional;

public interface DepartmentPersistencePort {

    Optional<DepartmentModel> getByDepartmentById(Long id);
    List<DepartmentModel> findAll();
    Optional<DepartmentModel> getByDepartmentByName(String name);

}