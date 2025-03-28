package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.CityDtoMapper;
import com.casas.casas.application.services.CityService;
import com.casas.casas.domain.ports.in.CityServicePort;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityServicePort cityServicePort;
    private final CityDtoMapper cityDtoMapper;

    @Override
    public SaveLocationResponse save(SaveCityRequest request) {
        cityServicePort.save(cityDtoMapper.requestToModel(request));
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
