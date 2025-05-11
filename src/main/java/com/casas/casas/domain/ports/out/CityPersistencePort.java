package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;

import java.util.List;
import java.util.Optional;

public interface CityPersistencePort {

    void save(CityModel cityModel);
    List<CityModel> findAll();


    Optional<CityModel> getByCityById(Long id);

    Optional<CityModel> getByCityByName(String name);

    List<CityModel> findByDepartmentId(Long departmentId);


}
