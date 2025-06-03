package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveHouseRequest;
import com.casas.casas.application.dto.response.HouseResponse;
import com.casas.casas.application.dto.response.SaveHouseResponse;
import com.casas.casas.application.dto.response.SellerHouseResponse;
import com.casas.casas.domain.utils.page.PagedResult;

public interface HouseService {
    SaveHouseResponse save(SaveHouseRequest request);
    PagedResult<HouseResponse> getHouseFiltered (Integer page, Integer size, String category, String name,
                                                 Integer numberOfRooms,
                                                 Integer numberOfBathrooms,Integer minPrice, Integer maxPrice,
                                                 String location, String sortBy, boolean orderAsc);
    HouseResponse getById(Long id);
    PagedResult<SellerHouseResponse> getHousesBySellerFiltered(Long sellerId, Integer page, Integer size, Long id,
                                                               String name, String category, Integer minPrice,
                                                               String location, boolean orderAsc);

}
