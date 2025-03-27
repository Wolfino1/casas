package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.LocationAlreadyExistsException;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class LocationUseCase implements LocationServicePort {
    private final LocationPersistencePort locationPersistencePort;

    public LocationUseCase(LocationPersistencePort locationPersistencePort) {
        this.locationPersistencePort = locationPersistencePort;
    }

    @Override
    public void save(LocationModel locationModel) {
        Optional<LocationModel> existingLocation = locationPersistencePort
                .getByCityAndDepartment(locationModel.getCity(), locationModel.getDepartment());

        if (existingLocation.isPresent()) {
            throw new LocationAlreadyExistsException();
        }

        locationPersistencePort.save(locationModel);
    }

    @Override
    public PagedResult<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        return locationPersistencePort.getFilters(page, size, city, department, orderAsc);
    }
}
