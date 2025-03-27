package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.infrastructure.entities.LocationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface LocationEntityMapper {
    LocationEntity modelToEntity(LocationModel locationModel);
    LocationModel entityToModel(LocationEntity locationEntity);
}

