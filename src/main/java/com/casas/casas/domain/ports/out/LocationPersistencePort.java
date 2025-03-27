package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.page.PagedResult;

import java.util.Optional;

public interface LocationPersistencePort {
    void save(LocationModel locationModel);
    Optional<LocationModel> getByCityAndDepartment(String city, String department);
    PagedResult<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc);
}
