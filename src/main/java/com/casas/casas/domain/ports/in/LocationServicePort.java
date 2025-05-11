package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.page.PagedResult;

import java.util.List;
import java.util.Optional;

public interface LocationServicePort {
    void save(LocationModel locationModel);
    PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, Long idDepartment, String search, boolean orderAsc);
    Long getIdByName(String name);
    LocationModel getById(Long id);
    Optional<Long> findByCityName(String cityName);
    Optional<Long> getIdByDepartmentName(String departmentName);
    List<Long> getAllIdsByDepartmentName(String departmentName);
}
