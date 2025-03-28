package com.casas.casas.domain.usecases;

import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.in.DepartmentServicePort;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;

import java.util.Optional;
public class DepartmentUseCase implements DepartmentServicePort {

    private final DepartmentPersistencePort departmentPersistencePort;

    public DepartmentUseCase(DepartmentPersistencePort departmentPersistencePort) {
        this.departmentPersistencePort = departmentPersistencePort;
    }

    @Override
    public Optional<DepartmentModel> getById(Long id) {
        return departmentPersistencePort.getByDepartmentById(id);
    }
}
