package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.HomeModel;
import com.casas.casas.infrastructure.entities.HomeEntity;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")

public interface HomeEntityMapper {
    HomeEntity modelToEntity(HomeModel homeModel);
    HomeModel entityToModel(HomeEntity homeEntity);
    List<HomeModel> entityListToModelList(List<HomeEntity> home);
}

