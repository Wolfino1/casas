package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.HouseServicePort;
import com.casas.casas.domain.ports.out.HousePersistencePort;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;

import java.time.LocalDate;
import java.util.Optional;

public class HouseUseCase implements HouseServicePort {
    private final HousePersistencePort housePersistencePort;
    private final LocationUseCase locationUseCase;
    private final CategoryUseCase categoryUseCase;

    public HouseUseCase(HousePersistencePort housePersistencePort,
                        LocationUseCase locationUseCase,
                        CategoryUseCase categoryUseCase) {
        this.housePersistencePort = housePersistencePort;
        this.locationUseCase = locationUseCase;
        this.categoryUseCase = categoryUseCase;
    }

    @Override
    public void save(HouseModel houseModel) {
        Optional<CategoryModel> categoryModel = Optional.ofNullable(categoryUseCase.getById(houseModel.getIdCategory()));
        Optional<LocationModel> locationModel = Optional.ofNullable(locationUseCase.getById(houseModel.getIdLocation()));

        if (categoryModel.isEmpty()) {
            throw new EmptyException(DomainConstants.CATEGORY_DOES_NOT_EXIST);
        }

        if (locationModel.isEmpty()) {
            throw new EmptyException(DomainConstants.LOCATION_DOES_NOT_EXIST);
        }
        if (housePersistencePort.existsByAddress(houseModel.getAddress())) {
            throw new IllegalArgumentException(DomainConstants.ADDRESS_ALREADY_EXISTS);
        }
        if (houseModel.getPublishActivationDate() != null &&
                houseModel.getPublishActivationDate().isBefore(LocalDate.now().plusDays(DomainConstants.DAYS_TO_ADD))) {
            houseModel.setIdPubStatus(DomainConstants.PUB_STATUS_IS_ACTIVE);
        } else {
            houseModel.setIdPubStatus(DomainConstants.PUB_STATUS_IS_DRAFT);
        }

        housePersistencePort.save(houseModel);
    }

    @Override
    public PagedResult<HouseModel> getFilters(Integer page, Integer size, Long idLocation, Long idCategory, Integer numberOfRooms,
                                              Integer numberOfBathrooms, Integer minPrice, Integer maxPrice, String sortBy, boolean orderAsc) {
        return housePersistencePort.getFilters(page, size, idLocation, idCategory, numberOfRooms,
                numberOfBathrooms, minPrice, maxPrice, sortBy , orderAsc);
    }

    @Override
    public HouseModel getById(Long id) {
        HouseModel house = housePersistencePort.getById(id);

        if (house == null) {
            throw new EmptyException(DomainConstants.HOUSE_DOES_NOT_EXIST);
        }

        return house;
    }

}
