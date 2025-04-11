package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.utils.page.PagedResult;

public interface HousePersistencePort {
    void save(HouseModel houseModel);
    PagedResult<HouseModel> getFilters(Integer page, Integer size, Long idLocation, Long idCategory,
                                       Integer numberOfRooms, Integer numberOfBathrooms,
                                       Integer minPrice, Integer maxPrice, String sortBy, boolean orderAsc);
    boolean existsByAddress(String address);

}
