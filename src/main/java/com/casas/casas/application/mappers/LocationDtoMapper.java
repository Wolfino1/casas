package com.casas.casas.application.mappers;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.CityResponse;
import com.casas.casas.application.dto.response.DepartmentResponse;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.model.LocationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationDtoMapper {
    LocationModel requestToModel(SaveLocationRequest saveLocationRequest);
    @Mapping(source = "city", target = "cityResponse")
    LocationResponse modelToResponse(LocationModel locationModel);

    default CityResponse toCityResponse(CityModel cityModel) {
        if (cityModel == null) {
            return null;
        }
        return new CityResponse(
                cityModel.getId(),
                cityModel.getName(),
                cityModel.getDescription(),
                toDepartmentResponse(cityModel.getDepartment())
        );
    }

    default DepartmentResponse toDepartmentResponse(DepartmentModel departmentModel) {
        if (departmentModel == null) {
            return null;
        }
        return new DepartmentResponse(
                departmentModel.getId(),
                departmentModel.getName(),
                departmentModel.getDescription()
        );
    }
}
