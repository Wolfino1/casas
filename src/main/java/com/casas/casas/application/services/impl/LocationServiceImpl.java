package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.LocationDtoMapper;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationServicePort locationServicePort;
    private final LocationDtoMapper locationDtoMapper;

    @Override
    public SaveLocationResponse save(SaveLocationRequest request) {
        locationServicePort.save(locationDtoMapper.requestToModel(request));
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public Page<LocationResponse> getAllLocationsFilters(Integer page, Integer size, String city, String department, boolean orderAsc) {
        return locationServicePort.getFilters(page, size, city, department, orderAsc).map(locationDtoMapper::modelToResponse);
    }
}

