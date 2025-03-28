package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;

import java.util.Optional;

public interface CityServicePort {

    void save(CityModel cityModel);
    Optional<CityModel> getById(Long id);


}
