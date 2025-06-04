package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.LocationDtoMapper;
import com.casas.casas.application.mappers.PageMapperApplication;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationServicePort locationServicePort;
    private final LocationDtoMapper locationDtoMapper;
    private final LocationPersistencePort locationPersistencePort;
    private final PageMapperApplication pageMapper;


    @Override
    public SaveLocationResponse save(SaveLocationRequest request) {
        locationServicePort.save(locationDtoMapper.requestToModel(request));
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedResult<LocationResponse> getAllLocationsFilters(Integer page, Integer size, Long idCity, Long idDepartment, String search, boolean orderAsc) {
        PagedResult<LocationModel> locationModelPagedResult = locationServicePort.getFilters(page, size, idCity, idDepartment, search, orderAsc);
        List<LocationResponse> content = locationModelPagedResult.getContent().stream()
                .map(locationDtoMapper::modelToResponse)
                .toList();
        return pageMapper.fromPage(content, locationModelPagedResult);
    }

    @Override
    public Long getIdByName(String name) {
        return locationPersistencePort.findByName(name)
                .map(LocationModel::getId)
                .orElseThrow(() -> new RuntimeException(DomainConstants.LOCATION_DOES_NOT_EXIST));
    }
    @Override
    public Optional<Long> getIdByCityName(String cityName) {
        Optional<LocationModel> result = locationPersistencePort.findByCityName(cityName);
        return result.map(LocationModel::getId);
    }

    @Override
    public Optional<Long> getIdByDepartmentName(String departmentName) {
        return locationServicePort.getIdByDepartmentName(departmentName);
    }

    @Override
    public String getNameById(Long idLocation) {
        LocationModel model = locationServicePort.getById(idLocation);
        if (model == null) {
            throw new IllegalArgumentException(DomainConstants.LOCATION_DOES_NOT_EXIST);
        }
        return model.getName();
    }

}

