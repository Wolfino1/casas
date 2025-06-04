package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveHouseRequest;
import com.casas.casas.application.dto.response.HouseResponse;
import com.casas.casas.application.dto.response.SaveHouseResponse;
import com.casas.casas.application.dto.response.SellerHouseResponse;
import com.casas.casas.application.mappers.HouseDtoMapper;
import com.casas.casas.application.mappers.PageMapperApplication;
import com.casas.casas.application.services.CategoryService;
import com.casas.casas.application.services.HouseService;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.ports.in.HouseServicePort;
import com.casas.casas.domain.ports.out.HousePersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseServicePort houseServicePort;
    private final HouseDtoMapper houseDtoMapper;
    private final PageMapperApplication pageMapper;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final HouseDtoMapper mapper;
    private final HousePersistencePort housePersistencePort;


    @Override
    public SaveHouseResponse save(HouseModel model) {
        houseServicePort.save(model);
        return new SaveHouseResponse(Constants.SAVE_HOUSE_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    public PagedResult<HouseResponse> getHouseFiltered(Integer page, Integer size, String category, String name,
                                                       Integer numberOfRooms, Integer numberOfBathrooms,
                                                       Integer minPrice, Integer maxPrice, String location,
                                                       String sortBy, boolean orderAsc) {
        Long idCategory = (category != null) ? categoryService.getIdByName(category) : null;
        Long idLocation = null;

        if (location != null) {
            try {
                idLocation = locationService.getIdByName(location);
            } catch (Exception e) {
                idLocation = locationService.getIdByCityName(location).orElse(
                        locationService.getIdByDepartmentName(location).orElse(null));
            }
        }

        PagedResult<HouseModel> houseModelPagedResult = houseServicePort.getFilters(
                page, size, idLocation, idCategory, name, numberOfRooms, numberOfBathrooms,
                minPrice, maxPrice, sortBy, orderAsc);

        List<HouseResponse> content = houseModelPagedResult.getContent().stream()
                .map(houseDtoMapper::modelToResponse)
                .toList();

        return pageMapper.fromPage(content, houseModelPagedResult);
    }

    @Override
    @Transactional(readOnly = true)
    public HouseResponse getById(Long id) {
        HouseModel model = houseServicePort.getById(id);
        return mapper.modelToResponse(model);
    }

    @Override
    public PagedResult<SellerHouseResponse> getHousesBySellerFiltered(Long sellerId, Integer page, Integer size,
                                                                      Long id, String name, String category, Integer minPrice,
                                                                      String location, boolean orderAsc) {

        Long idCategory = (category != null) ? categoryService.getIdByName(category) : null;
        Long idLocation = null;

        if (location != null) {
            try {
                idLocation = locationService.getIdByName(location);
            } catch (Exception e) {
                idLocation = locationService.getIdByCityName(location).orElse(
                        locationService.getIdByDepartmentName(location).orElse(null));
            }
        }

        PagedResult<HouseModel> models = houseServicePort.getFiltersBySeller(
                sellerId, page, size, id, name, idLocation, idCategory, minPrice, orderAsc
        );
        List<SellerHouseResponse> dtoList = models.getContent().stream()
                .map(houseDtoMapper::modelToSellerResponse)
                .toList();
        return pageMapper.fromPage(dtoList, models);
    }

}

