package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.validations.LocationFilters;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationServicePort {
    void save(LocationModel locationModel);
    Page<LocationModel> getFilters(Integer page, Integer size, String city, String department, boolean orderAsc);
}
