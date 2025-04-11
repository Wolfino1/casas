package com.casas.casas.application.mappers;

import com.casas.casas.application.dto.request.SaveHouseRequest;
import com.casas.casas.application.dto.response.*;
import com.casas.casas.domain.model.*;
import com.casas.casas.domain.ports.in.LocationServicePort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class HouseDtoMapper {

    @Autowired
    protected LocationServicePort locationServicePort;

    public abstract HouseModel requestToModel(SaveHouseRequest saveHouseRequest);

    @Mapping(source = "idLocation", target = "Location", qualifiedByName = "mapLocation")
    @Mapping(source = "idCategory", target = "Category", qualifiedByName = "mapCategory")
    @Mapping(source = "price", target = "priceMin")
    @Mapping(source = "price", target = "priceMax")
    public abstract HouseResponse modelToResponse(HouseModel houseModel);

    @Named("mapCategory")
    public String mapCategory(Long idCategory) {
        if (idCategory == null) return "Desconocido";
        return String.valueOf(idCategory);
    }

    @Named("mapLocation")
    public LocationResponse mapLocation(Long idLocation) {
        if (idLocation == null) return null;

        LocationModel location = locationServicePort.getById(idLocation);
        return toLocationResponse(location);
    }

    public LocationResponse toLocationResponse(LocationModel locationModel) {
        if (locationModel == null) return null;
        return new LocationResponse(
                locationModel.getId(),
                locationModel.getName(),
                toCityResponse(locationModel.getCity())
        );
    }

    public CityResponse toCityResponse(CityModel cityModel) {
        if (cityModel == null) return null;
        return new CityResponse(
                cityModel.getId(),
                cityModel.getName(),
                cityModel.getDescription(),
                toDepartmentResponse(cityModel.getDepartment())
        );
    }

    public DepartmentResponse toDepartmentResponse(DepartmentModel departmentModel) {
        if (departmentModel == null) return null;
        return new DepartmentResponse(
                departmentModel.getId(),
                departmentModel.getName(),
                departmentModel.getDescription()
        );
    }
}




