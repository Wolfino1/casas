package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.entities.LocationEntity;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.mappers.PageMapperInfra;
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
    private final PageMapperInfra pageMapperInfra;


    @Override
    public void save(LocationModel locationModel) {
        locationRepository.save(locationEntityMapper.modelToEntity(locationModel));
    }

    @Override
    public Optional<LocationModel> getByCityAndDepartment(String city, String department) {
        return locationRepository.findByCityAndDepartment(city, department)
                .map(locationEntityMapper::entityToModel);
    }

    @Override
    public PagedResult<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        Pageable pagination;
        if (orderAsc) {
            pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
        } else {
            pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
        }

        Page<LocationEntity> pageResult;

        if (city != null && department != null) {
            pageResult = locationRepository.findByCityContainingAndDepartmentContaining(city, department, pagination);
        } else if (city != null) {
            pageResult = locationRepository.findByCityContaining(city, pagination);
        } else if (department != null) {
            pageResult = locationRepository.findByDepartmentContaining(department, pagination);
        } else {
            pageResult = locationRepository.findAll(pagination);
        }
        return pageMapperInfra.fromPage(pageResult.map(locationEntityMapper::entityToModel));
    }

}



