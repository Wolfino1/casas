package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.model.LocationModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface LocationPersistencePort {
    void save(LocationModel locationModel);
    Optional<LocationModel> getByCityAndDepartment(String city, String department);
    Page<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc);
}
