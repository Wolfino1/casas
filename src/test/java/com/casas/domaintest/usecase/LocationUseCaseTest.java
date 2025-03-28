package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.usecases.CityUseCase;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.domain.utils.page.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseTest {

    @Mock
    private LocationPersistencePort locationPersistencePort;

    @Mock
    private CityUseCase cityUseCase;

    @InjectMocks
    private LocationUseCase locationUseCase;

    private LocationModel locationModel;
    private CityModel cityModel;

    @BeforeEach
    void setUp() {
        DepartmentModel departmentModel = new DepartmentModel(1L, "Santander", "Departamento hermoso");
        cityModel = new CityModel(1L, "Bucaramanga", "Ciudad Bonita", 1L, departmentModel);

        cityModel = new CityModel(1L, "Bucaramanga", "Ciudad Bonita", 1L,departmentModel);
        locationModel = new LocationModel(1L, "Centro", 1L, cityModel);
    }

    @Test
    void save_ShouldSaveLocation_WhenCityExists() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.of(cityModel));

        assertDoesNotThrow(() -> locationUseCase.save(locationModel));
        verify(locationPersistencePort, times(1)).save(locationModel);
    }

    @Test
    void save_ShouldThrowException_WhenCityDoesNotExist() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyException.class, () -> locationUseCase.save(locationModel));
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void getFilters_ShouldReturnPagedLocations() {
        PagedResult<LocationModel> pagedResult = new PagedResult<>(List.of(locationModel), 1, 1, 1);
        when(locationPersistencePort.getFilters(0, 10, 1L, true)).thenReturn(pagedResult);

        PagedResult<LocationModel> result = locationUseCase.getFilters(0, 10, 1L, true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertFalse(result.getContent().isEmpty());
        verify(locationPersistencePort, times(1)).getFilters(0, 10, 1L, true);
    }
}
