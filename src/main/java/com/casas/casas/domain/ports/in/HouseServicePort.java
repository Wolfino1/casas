package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.utils.page.PagedResult;

public interface HouseServicePort {
    void save(HouseModel houseModel);
    PagedResult<HouseModel> getFilters(Integer page, Integer size, Long idLocation, Long idCategory,
                                       String name, Integer numberOfRooms, Integer numberOfBathrooms,
                                       Integer minPrice, Integer maxPrice, String sortBy, boolean orderAsc);
    HouseModel getById(Long id);
    PagedResult<HouseModel> getFiltersBySeller(Long sellerId, Integer page, Integer size, Long id, String name, Long idLocation, Long idCategory,
                                               Integer minPrice, boolean orderAsc);



}
