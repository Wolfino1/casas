package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.LocationRepository;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public LocationModel getByDepartment(String locationDepartment) {
        return locationEntityMapper.entityToModel(locationRepository.findByCity(locationDepartment).orElse(null));
    }

    @Override
    public List<LocationModel> get(Integer page, Integer size, boolean orderAsc) {
        Pageable pagination;
        if (orderAsc) pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
        else pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
        return locationEntityMapper.entityListToModelList(locationRepository.findAll(pagination).getContent());
    }

    @Override
    public List<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        Pageable pagination = PageRequest.of(page, size,
                orderAsc ? Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending() : Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());

        if (city != null && !city.trim().isEmpty() && department != null && !department.trim().isEmpty()) {
            return locationEntityMapper.entityListToModelList(
                    locationRepository.findByCityContainingAndDepartmentContaining(city, department, pagination).getContent()
            );
        }

        if (city != null && !city.trim().isEmpty()) {
            return locationEntityMapper.entityListToModelList(
                    locationRepository.findByCityContaining(city, pagination).getContent()
            );
        }

        if (department != null && !department.trim().isEmpty()) {
            return locationEntityMapper.entityListToModelList(
                    locationRepository.findByDepartmentContaining(department, pagination).getContent()
            );
        }

        return locationEntityMapper.entityListToModelList(
                locationRepository.findAll(pagination).getContent()
        );
    }
}


