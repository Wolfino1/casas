package com.casas.domaintest.usecase;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.HousePersistencePort;
import com.casas.casas.domain.usecases.HouseUseCase;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.domain.usecases.CategoryUseCase;

import com.casas.casas.domain.utils.page.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseUseCaseTest {

    @Mock
    private HousePersistencePort housePersistencePort;

    @Mock
    private LocationUseCase locationUseCase;

    @Mock
    private CategoryUseCase categoryUseCase;

    @InjectMocks
    private HouseUseCase houseUseCase;

    private final Long categoryId = 1L;
    private final Long locationId = 2L;
    private HouseModel baseModel;

    @BeforeEach
    void setUp() {
        baseModel = new HouseModel(
                null, "Casa bonita", "Calle 28","Descripción",
                1L, 3, 2,
                500000000,locationId,
                LocalDate.now(),
                null,
                null
        );
    }

    @Test
    void save_WhenActivationDateIsToday_SetsPubStatusTo2() {
        when(categoryUseCase.getById(categoryId)).thenReturn(new CategoryModel(categoryId, "Residencial", "Desc"));
        when(locationUseCase.getById(locationId)).thenReturn(new LocationModel(locationId, "Zona Norte", 1L, null));

        houseUseCase.save(baseModel);

        assertEquals(2L, baseModel.getIdPubStatus());
        verify(housePersistencePort).save(baseModel);
    }

    @Test
    void save_WhenActivationDateIsFuture_SetsPubStatusTo1() {
        HouseModel futureHouse = new HouseModel(
                null, "Casa bonita", "Calle 28","Descripción",
                1L, 3, 2,
                500000000,locationId,
                LocalDate.now().plusDays(1),
                null,
                null
        );

        when(categoryUseCase.getById(categoryId)).thenReturn(new CategoryModel(categoryId, "Residencial", "Desc"));
        when(locationUseCase.getById(locationId)).thenReturn(new LocationModel(locationId, "Ruitoque", 1L, null));
        when(housePersistencePort.existsByAddress(futureHouse.getAddress())).thenReturn(false);

        houseUseCase.save(futureHouse);

        assertEquals(1L, futureHouse.getIdPubStatus());
    }

    @Test
    void getFilters_DelegatesToPersistencePort() {
        PagedResult<HouseModel> expected = new PagedResult<>(List.of(baseModel), 1, 1, 10);
        String sortBy = "price";

        when(housePersistencePort.getFilters(0, 10, locationId, categoryId, 3, 2, 400000000, 600000000, sortBy,true))
                .thenReturn(expected);

        PagedResult<HouseModel> result = houseUseCase.getFilters(0, 10, locationId, categoryId, 3, 2, 400000000, 600000000, sortBy,true);

        assertEquals(expected, result);
        verify(housePersistencePort).getFilters(0, 10, locationId, categoryId, 3, 2, 400000000, 600000000, sortBy,true);
    }

}