package com.casas.casas.application.mappers;

import com.casas.casas.application.dto.request.SaveHomeRequest;
import com.casas.casas.application.dto.response.HomeResponse;
import com.casas.casas.domain.model.HomeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HomeDtoMapper {
    HomeModel requestToModel(SaveHomeRequest homeCategoryRequest);
    HomeResponse modelToResponse(HomeModel homeModel);
    List<HomeResponse> modelListToResponseList(List<HomeModel> homes);
}
