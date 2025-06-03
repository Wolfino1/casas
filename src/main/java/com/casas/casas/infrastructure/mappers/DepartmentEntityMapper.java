package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.infrastructure.entities.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentEntityMapper {
    DepartmentModel entityToModel(DepartmentEntity departmentEntity);
}
