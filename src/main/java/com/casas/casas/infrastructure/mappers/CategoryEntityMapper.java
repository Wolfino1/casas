package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.infrastructure.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CategoryEntityMapper {
    CategoryEntity modelToEntity(CategoryModel categoryModel);
    CategoryModel entityToModel(CategoryEntity categoryEntity);
}
