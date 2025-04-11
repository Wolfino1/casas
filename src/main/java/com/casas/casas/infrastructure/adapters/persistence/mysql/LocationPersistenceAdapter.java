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

import java.util.List;
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
        locationRepository.save(locationEntityMapper.toEntity(locationModel,locationModel.getCity()));
    }

    @Override
    public PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, Long idDepartment, boolean orderAsc) {
        Pageable pagination = orderAsc
                ? PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending())
                : PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());

        Page<LocationEntity> pageResult;

        if (idCity != null) {
            pageResult = locationRepository.findByCityId(idCity, pagination);
        } else if (idDepartment != null) {
            pageResult = locationRepository.findByCity_Department_Id(idDepartment, pagination);
        } else {
            pageResult = locationRepository.findAll(pagination);
        }

        return pageMapperInfra.fromPage(pageResult.map(locationEntityMapper::entityToModel));
    }

    @Override
    public Optional<LocationModel> findById(Long id) {
        return locationRepository.findById(id)
                .map(locationEntityMapper::entityToModel);
    }

    @Override
    public Optional<LocationModel> findByName(String name) {
        return locationRepository.findByName(name)
                .map(locationEntityMapper::entityToModel);
    }
    @Override
    public Optional<LocationModel> findByCityName(String cityName) {
        return locationRepository.findByCityName(cityName)
                .map(locationEntityMapper::entityToModel);
    }
    @Override
    public List<LocationModel> findByDepartmentName(String departmentName) {
        return locationRepository.findAllByDepartmentName(departmentName)
                .stream()
                .map(locationEntityMapper::entityToModel)
                .toList();
    }
}
