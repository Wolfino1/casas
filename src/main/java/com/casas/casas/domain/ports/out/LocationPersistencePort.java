package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.model.LocationModel;

import java.util.List;

public interface LocationPersistencePort {
    void save(LocationModel locationModel);
    LocationModel getByDepartment(String locationDepartment);
    List<LocationModel> get(Integer page, Integer size, boolean orderAsc);
    List<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc);

}
