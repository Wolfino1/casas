package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.infrastructure.entities.LocationEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {
    @Mapping(target = "city", source = "city")
    LocationEntity toEntity(LocationModel model, @Context CityModel city);
    LocationModel entityToModel (LocationEntity entity);
}

