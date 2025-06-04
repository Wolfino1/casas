package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.ListingDateExceedTimeException;
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
        LocalDate limit = LocalDate.now().plusMonths(DomainConstants.MONTHS_TO_ADD);
        if (houseModel.getPublishActivationDate() != null &&
                houseModel.getPublishActivationDate().isAfter(limit)) {
            throw new ListingDateExceedTimeException(
                    DomainConstants.LISTING_DATE_IS_IN_MORE_THAN_ONE_MONTH
            );
        }
        if (houseModel.getPublishActivationDate() != null &&
            houseModel.getPublishActivationDate().isBefore(LocalDate.now())) {
            houseModel.setIdPubStatus(DomainConstants.PUB_STATUS_IS_ACTIVE);
        } else {
            houseModel.setIdPubStatus(DomainConstants.PUB_STATUS_IS_DRAFT);
        }

        housePersistencePort.save(houseModel);
    }

    @Override
    public PagedResult<HouseModel> getFilters(Integer page, Integer size, Long idLocation, Long idCategory,
                                              String name, Integer numberOfRooms,
                                              Integer numberOfBathrooms, Integer minPrice, Integer maxPrice, String sortBy, boolean orderAsc) {
        return housePersistencePort.getFilters(page, size, idLocation, idCategory, name, numberOfRooms,
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

    @Override
    public PagedResult<HouseModel> getFiltersBySeller(Long sellerId, Integer page, Integer size, Long id, String name, Long idLocation, Long idCategory,
                                                      Integer minPrice, boolean orderAsc) {

        return housePersistencePort.getFiltersBySeller(sellerId, page, size, id, name, idLocation, idCategory,
                 minPrice,  orderAsc);
    }
}
