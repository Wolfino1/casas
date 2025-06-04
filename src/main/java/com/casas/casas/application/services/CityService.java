package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.domain.model.CityModel;

import java.util.List;

public interface CityService {

    SaveLocationResponse save(SaveCityRequest request);
    List <CityModel> getAllCities();
    CityModel getCityById(Long id);
    List<CityModel> getCitiesByDepartmentId(Long departmentId);


}

