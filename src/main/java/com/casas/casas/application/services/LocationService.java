package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import org.springframework.data.domain.Page;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
    Page<LocationResponse> getAllLocationsFilters(Integer page, Integer size, String city, String department, boolean orderAsc);

}