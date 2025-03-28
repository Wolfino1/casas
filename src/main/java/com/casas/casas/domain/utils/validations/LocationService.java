package com.casas.casas.domain.utils.validations;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;

public class LocationService {
    private final LocationPersistencePort locationPersistencePort;

    public LocationService(LocationPersistencePort locationPersistencePort) {
        this.locationPersistencePort = locationPersistencePort;
    }

    public PagedResult<LocationModel> getFilteredLocations(Integer page, Integer size,Long idCity, boolean orderAsc) {
        return locationPersistencePort.getFilters(page, size, idCity, orderAsc);
    }

    private void validateFilters(String city, String department) {
        if ((city != null && city.trim().isEmpty()) || (department != null && department.trim().isEmpty())) {
            throw new IllegalArgumentException("City and department cannot be empty");
        }
    }
}
