package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;

import java.util.Optional;

public class LocationUseCase implements LocationServicePort {
    private final LocationPersistencePort locationPersistencePort;
    private final CityUseCase cityUseCase;

    public LocationUseCase(LocationPersistencePort locationPersistencePort, CityUseCase cityUseCase) {
        this.locationPersistencePort = locationPersistencePort;
        this.cityUseCase = cityUseCase;
    }

    @Override
    public void save(LocationModel locationModel) {
        Optional<CityModel> cityModel = cityUseCase.getById(locationModel.getIdCity());
        if (cityModel.isEmpty()) {
            throw new EmptyException("Department not found");
        }
        locationModel.setCity(cityModel.get());
        locationPersistencePort.save(locationModel);
    }

    @Override
    public PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, boolean orderAsc) {
        return locationPersistencePort.getFilters(page, size, idCity, orderAsc);
    }
}
