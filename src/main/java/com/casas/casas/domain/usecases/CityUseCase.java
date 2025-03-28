package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.in.CityServicePort;
import com.casas.casas.domain.ports.out.CityPersistencePort;

import java.util.Optional;

public class CityUseCase implements CityServicePort {

    private final CityPersistencePort cityPersistencePort;
    private final DepartmentUseCase departmentUseCase;

    public CityUseCase(CityPersistencePort cityPersistencePort, DepartmentUseCase departmentUseCase) {
        this.cityPersistencePort = cityPersistencePort;
        this.departmentUseCase = departmentUseCase;
    }

    @Override
    public void save(CityModel cityModel) {
        Optional<DepartmentModel> departmentModel = departmentUseCase.getById(cityModel.getIdDepartment());
        if (departmentModel.isEmpty()) {
            throw new EmptyException("Department not found");
        }
        cityModel.setDepartment(departmentModel.get());
        cityPersistencePort.save(cityModel);
    }
    @Override
    public Optional<CityModel> getById(Long id) {
        return cityPersistencePort.getByCityById(id);
    }
}

