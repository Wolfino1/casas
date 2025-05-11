package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.CityDtoMapper;
import com.casas.casas.application.services.CityService;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.in.CityServicePort;
import com.casas.casas.domain.ports.out.CityPersistencePort;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityServicePort cityServicePort;
    private final CityDtoMapper cityDtoMapper;
    private final CityPersistencePort cityPersistencePort;

    @Override
    public SaveLocationResponse save(SaveCityRequest request) {
        cityServicePort.save(cityDtoMapper.requestToModel(request));
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public List<CityModel> getAllCities() {
        return cityPersistencePort.findAll();
    }

    @Override
    public CityModel getCityById(Long id) {
        return cityPersistencePort.getByCityById(id)
                .orElseThrow(() -> new RuntimeException("City not found for id: " + id));
    }
    @Override
    public List<CityModel> getCitiesByDepartmentId(Long departmentId) {
        return cityPersistencePort.findByDepartmentId(departmentId);
    }

}
