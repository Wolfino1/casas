package com.casas.casas.application.mappers;

import com.casas.casas.application.dto.response.DepartmentResponse;
import com.casas.casas.domain.model.DepartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentDtoMapper {
    DepartmentResponse modelToResponse(DepartmentModel departmentModel);
}
