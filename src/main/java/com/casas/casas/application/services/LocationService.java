package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.domain.utils.page.PagedResult;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
    PagedResult<LocationResponse> getAllLocationsFilters(Integer page, Integer size, Long idCity, boolean orderAsc);
}