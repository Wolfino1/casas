package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.DepartmentModel;

import java.util.Optional;

public interface DepartmentServicePort {

    Optional<DepartmentModel> getById(Long id);

}