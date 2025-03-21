package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.infrastructure.entities.LocationEntity;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")

public interface LocationEntityMapper {
    LocationEntity modelToEntity(LocationModel locationModel);
    List<LocationModel> entityListToModelList(List<LocationEntity> location);
    LocationModel entityToModel(LocationEntity locationEntity);
}

