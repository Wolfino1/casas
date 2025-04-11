package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.PubStatusModel;
import com.casas.casas.infrastructure.entities.PubStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PubStatusEntityMapper {
    PubStatusModel entityToModel(PubStatusEntity pubStatusEntity);
}
