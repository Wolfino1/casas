package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.utils.page.PagedResult;

public interface LocationPersistencePort {
    void save(LocationModel locationModel);
    PagedResult<LocationModel> getFilters(Integer page, Integer size, Long idCity, boolean orderAsc);
}
