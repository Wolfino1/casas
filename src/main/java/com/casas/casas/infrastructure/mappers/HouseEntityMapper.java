package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.infrastructure.entities.CategoryEntity;
import com.casas.casas.infrastructure.entities.HouseEntity;
import com.casas.casas.infrastructure.entities.LocationEntity;
import com.casas.casas.infrastructure.entities.PubStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HouseEntityMapper {

    @Mapping(target = "category", source = "idCategory", qualifiedByName = "idToCategoryEntity")
    @Mapping(target = "location", source = "idLocation", qualifiedByName = "idToLocationEntity")
    @Mapping(target = "pubStatus", source = "idPubStatus", qualifiedByName = "idToPubStatusEntity")
    HouseEntity modelToEntity(HouseModel model);

    @Mapping(target = "idCategory", source = "category.id")
    @Mapping(target = "idLocation", source = "location.id")
    @Mapping(target = "idPubStatus", source = "pubStatus.id")
    HouseModel entityToModel(HouseEntity entity);

    @Named("idToCategoryEntity")
    default CategoryEntity idToCategoryEntity(Long id) {
        if (id == null) return null;
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        return category;
    }

    @Named("idToLocationEntity")
    default LocationEntity idToLocationEntity(Long id) {
        if (id == null) return null;
        LocationEntity location = new LocationEntity();
        location.setId(id);
        return location;
    }

    @Named("idToPubStatusEntity")
    default PubStatusEntity idToPubStatusEntity(Long id) {
        if (id == null) return null;
        PubStatusEntity pubStatus = new PubStatusEntity();
        pubStatus.setId(id);
        return pubStatus;
    }
}
