package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;

import java.util.List;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
    List<LocationResponse> getLocations(Integer page, Integer size, boolean orderAsc);
    List<LocationResponse> getAllLocationsFilters(Integer page, Integer size, String city, String department, boolean orderAsc);

}