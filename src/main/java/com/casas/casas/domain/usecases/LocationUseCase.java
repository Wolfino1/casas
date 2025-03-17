package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.LocationAlreadyExistsException;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;

import java.util.List;

public class LocationUseCase implements LocationServicePort {
    private final LocationPersistencePort locationPersistencePort;

    public LocationUseCase(LocationPersistencePort locationPersistencePort) {
        this.locationPersistencePort = locationPersistencePort;
    }

    @Override
    public void save(LocationModel locationModel) {
        LocationModel location = locationPersistencePort.getByDepartment(locationModel.getDepartment());
        if (location != null) {
            throw new LocationAlreadyExistsException();
        }
        locationPersistencePort.save(locationModel);
    }

    @Override
    public List<LocationModel> get(Integer page, Integer size, boolean orderAsc) {
        return locationPersistencePort.get(page, size, orderAsc);
    }
    @Override
    public List<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        return locationPersistencePort.getFilters(page, size, city, department, orderAsc);
    }
}
