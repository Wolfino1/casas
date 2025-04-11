package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.infrastructure.entities.CityEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityEntityMapper {
    @Mapping(target = "department", source = "department")
    CityEntity toEntity(CityModel model, @Context DepartmentModel department);
    CityModel entityToModel (CityEntity entity);
}
