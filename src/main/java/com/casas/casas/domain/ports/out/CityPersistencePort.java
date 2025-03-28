package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CityModel;

import java.util.Optional;

public interface CityPersistencePort {

    void save(CityModel cityModel);

    Optional<CityModel> getByCityById(Long id);

    Optional<CityModel> getByCityByName(String name);

}
