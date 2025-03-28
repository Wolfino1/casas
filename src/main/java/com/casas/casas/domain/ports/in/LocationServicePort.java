package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.page.PagedResult;

public interface LocationServicePort {
    void save(LocationModel locationModel);
    PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, boolean orderAsc);
}
