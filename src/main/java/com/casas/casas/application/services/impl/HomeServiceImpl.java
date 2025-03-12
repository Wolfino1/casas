package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveHomeRequest;
import com.casas.casas.application.dto.response.HomeResponse;
import com.casas.casas.application.dto.response.SaveHomeResponse;
import com.casas.casas.application.mappers.HomeDtoMapper;
import com.casas.casas.application.services.HomeService;
import com.casas.casas.domain.ports.in.HomeServicePort;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final HomeServicePort homeServicePort;
    private final HomeDtoMapper homeDtoMapper;

    @Override
    public SaveHomeResponse save(SaveHomeRequest request) {
        homeServicePort.save(homeDtoMapper.requestToModel(request));
        return new SaveHomeResponse(Constants.SAVE_HOME_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public List<HomeResponse> getHomes(Integer page, Integer size, boolean orderAsc) {
        return homeDtoMapper.modelListToResponseList(homeServicePort.get(page, size, orderAsc));
    }
}

