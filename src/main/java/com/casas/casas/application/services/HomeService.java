package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveHomeRequest;
import com.casas.casas.application.dto.response.HomeResponse;
import com.casas.casas.application.dto.response.SaveHomeResponse;

import java.util.List;

public interface HomeService {
    SaveHomeResponse save(SaveHomeRequest request);
    List<HomeResponse> getHomes(Integer page, Integer size, boolean orderAsc);
}