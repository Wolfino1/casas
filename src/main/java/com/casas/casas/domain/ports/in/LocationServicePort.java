package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.LocationModel;

import java.util.List;

public interface LocationServicePort {
    void save(LocationModel locationModel);
    List<LocationModel> get(Integer page, Integer size, boolean orderAsc);
    List<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc);
}
