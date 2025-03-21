package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.infrastructure.entities.LocationEntity;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.LocationRepository;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationPersistenceAdapter implements LocationPersistencePort {
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    public void save(LocationModel locationModel) {
        locationRepository.save(locationEntityMapper.modelToEntity(locationModel));
    }

    @Override
    public Optional<LocationModel> getByCityAndDepartment(String city, String department) {
        return locationRepository.findByCityAndDepartment(city, department)
                .map(LocationEntity::toModel);
    }

    @Override
    public Page<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        Pageable pagination = PageRequest.of(page, size,
                orderAsc ? Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending() : Sort.by(Constants.PAGEABLE_FIELD_NAME)
                        .descending());

        if (city != null && !city.trim().isEmpty() && department != null && !department.trim().isEmpty()) {
            return locationRepository.findByCityContainingAndDepartmentContaining(city, department, pagination)
                    .map(locationEntityMapper::entityToModel);
        }

        if (city != null && !city.trim().isEmpty()) {
            return locationRepository.findByCityContaining(city, pagination)
                    .map(locationEntityMapper::entityToModel);
        }

        if (department != null && !department.trim().isEmpty()) {
            return locationRepository.findByDepartmentContaining(department, pagination)
                    .map(locationEntityMapper::entityToModel);
        }

        return locationRepository.findAll(pagination)
                .map(locationEntityMapper::entityToModel);
    }
} // Hacer esta validación en el dominio



