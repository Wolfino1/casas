package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.domain.utils.page.PagedResult;

import java.util.Optional;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
    PagedResult<LocationResponse> getAllLocationsFilters(Integer page, Integer size, Long idCity, Long idDepartment, String search, boolean orderAsc);
    Long getIdByName(String name);
    public Optional<Long> getIdByCityName(String cityName);
    public Optional<Long> getIdByDepartmentName(String departmentName);
    String getNameById(Long idLocation);

}