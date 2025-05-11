package com.casas.casas.application.mappers;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.CityResponse;
import com.casas.casas.domain.model.CityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityDtoMapper {
    CityModel requestToModel(SaveCityRequest saveCityRequest);
    CityResponse modelToResponse(CityModel cityModel);}

