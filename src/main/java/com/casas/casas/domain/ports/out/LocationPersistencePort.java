package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.page.PagedResult;


import java.util.List;
import java.util.Optional;

public interface LocationPersistencePort {
    void save(LocationModel locationModel);
    PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, Long idDepartment, String search, boolean orderAsc);
    Optional<LocationModel> findById(Long id);
    Optional<LocationModel> findByName(String name);
    Optional<LocationModel> findByCityName(String cityName);
    List<LocationModel> findByDepartmentName(String departmentName);


}
