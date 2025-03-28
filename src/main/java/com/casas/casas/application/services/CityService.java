package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.SaveLocationResponse;

public interface CityService {

    SaveLocationResponse save(SaveCityRequest request);
}

