package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        cityModel = new CityModel(1L, "Bucaramanga", "Ciudad bonita", 1L, null);
        locationModel = new LocationModel(1L, "Zona Norte", 1L, cityModel);
    }

    @Test
    void save_validLocation_savesSuccessfully() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.of(cityModel));

        assertDoesNotThrow(() -> locationUseCase.save(locationModel));

        verify(locationPersistencePort).save(Mockito.any(LocationModel.class));
    }

    @Test
    void save_invalidCity_throwsException() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyException.class, () -> locationUseCase.save(locationModel));
    }

    @Test
    void getIdByName_foundInLocation_returnsId() {
        when(locationPersistencePort.findByName("Zona Norte"))
                .thenReturn(Optional.of(locationModel));

        Long result = locationUseCase.getIdByName("Zona Norte");

        assertEquals(1L, result);
    }

    @Test
    void getIdByName_foundInCity_returnsId() {
        when(locationPersistencePort.findByName("Ruitoque")).thenReturn(Optional.empty());
        when(locationPersistencePort.findByCityName("Ruitoque"))
                .thenReturn(Optional.of(locationModel));

        Long result = locationUseCase.getIdByName("Ruitoque");

        assertEquals(1L, result);
    }

    @Test
    void getIdByName_foundInDepartment_returnsId() {
        when(locationPersistencePort.findByName("Santander")).thenReturn(Optional.empty());
        when(locationPersistencePort.findByCityName("Santander")).thenReturn(Optional.empty());
        when(locationPersistencePort.findByDepartmentName("Santander"))
                .thenReturn(List.of(locationModel));

        Long result = locationUseCase.getIdByName("Santander");

        assertEquals(1L, result);
    }

    @Test
    void getIdByName_notFound_returnsNull() {
        when(locationPersistencePort.findByName("Nada")).thenReturn(Optional.empty());
        when(locationPersistencePort.findByCityName("Nada")).thenReturn(Optional.empty());
        when(locationPersistencePort.findByDepartmentName("Nada")).thenReturn(List.of());

        Long result = locationUseCase.getIdByName("Nada");

        assertNull(result);
    }
    @Test
    void getFilters_ReturnsPagedResult() {
        // Arrange
        int page = 0, size = 10;
        Long idCity = 1L, idDepartment = 2L;
        boolean orderAsc = true;

        PagedResult<LocationModel> expected = new PagedResult<>(List.of(), 0, 1, 10);
        when(locationPersistencePort.getFilters(page, size, idCity, idDepartment, orderAsc)).thenReturn(expected);

        PagedResult<LocationModel> result = locationUseCase.getFilters(page, size, idCity, idDepartment, orderAsc);

        assertEquals(expected, result);
        verify(locationPersistencePort).getFilters(page, size, idCity, idDepartment, orderAsc);
    }
    @Test
    void getById_WhenExists_ReturnsLocationModel() {
        Long id = 1L;
        LocationModel expected = new LocationModel(id, "Zona Norte", 3L, null);
        when(locationPersistencePort.findById(id)).thenReturn(Optional.of(expected));

        LocationModel result = locationUseCase.getById(id);

        assertEquals(expected, result);
        verify(locationPersistencePort).findById(id);
    }
    @Test
    void findByCityName_ReturnsIdIfExists() {
        String cityName = "Bucaramanga";
        LocationModel model = new LocationModel(2L, "Ruitoque", 5L, null);

        when(locationPersistencePort.findByCityName(cityName)).thenReturn(Optional.of(model));

        Optional<Long> result = locationUseCase.findByCityName(cityName);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get());
    }
    @Test
    void getIdByDepartmentName_ReturnsFirstMatchingId() {
        String departmentName = "Santander";
        List<LocationModel> models = List.of(
                new LocationModel(3L, "Cabecera", 2L, null),
                new LocationModel(4L, "Centro", 2L, null)
        );

        when(locationPersistencePort.findByDepartmentName(departmentName)).thenReturn(models);

        Optional<Long> result = locationUseCase.getIdByDepartmentName(departmentName);

        assertTrue(result.isPresent());
        assertEquals(3L, result.get());
    }
    @Test
    void getAllIdsByDepartmentName_ReturnsAllMatchingIds() {
        String departmentName = "Santander";
        List<LocationModel> models = List.of(
                new LocationModel(3L, "Cabecera", 2L, null),
                new LocationModel(4L, "Centro", 2L, null)
        );

        when(locationPersistencePort.findByDepartmentName(departmentName)).thenReturn(models);

        List<Long> ids = locationUseCase.getAllIdsByDepartmentName(departmentName);

        assertEquals(List.of(3L, 4L), ids);
    }
}

