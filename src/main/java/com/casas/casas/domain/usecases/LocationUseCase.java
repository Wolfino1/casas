package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
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
    public PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, Long idDepartment, boolean orderAsc) {
        return locationPersistencePort.getFilters(page, size, idCity, idDepartment, orderAsc);
    }

    @Override
    public LocationModel getById(Long id) {
        return locationPersistencePort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con ID: " + id));
    }
    @Override
    public Long getIdByName(String name) {
        Optional<LocationModel> locationByName = locationPersistencePort.findByName(name);
        if (locationByName.isPresent()) {
            return locationByName.get().getId();
        }

        Optional<LocationModel> locationByCity = locationPersistencePort.findByCityName(name);
        if (locationByCity.isPresent()) {
            return locationByCity.get().getId();
        }

        List<LocationModel> locationByDepartment = locationPersistencePort.findByDepartmentName(name);
        return locationByDepartment.stream()
                .map(LocationModel::getId)
                .findFirst()
                .orElse(null);
    }
    @Override
    public Optional<Long> findByCityName(String cityName) {
        return locationPersistencePort.findByCityName(cityName)
                .map(LocationModel::getId);
    }

    @Override
    public Optional<Long> getIdByDepartmentName(String departmentName) {
        return locationPersistencePort.findByDepartmentName(departmentName)
                .stream()
                .map(LocationModel::getId)
                .findFirst();
    }

    @Override
    public List<Long> getAllIdsByDepartmentName(String departmentName) {
        return locationPersistencePort.findByDepartmentName(departmentName)
                .stream()
                .map(LocationModel::getId)
                .toList();
    }
}
