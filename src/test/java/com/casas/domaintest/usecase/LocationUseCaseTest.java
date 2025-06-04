package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.domain.usecases.CityUseCase;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
        cityModel = new CityModel(1L, "Bucaramanga", "Ciudad bonita", 1L, null);
        locationModel = new LocationModel(1L, "Zona Norte", 1L, cityModel);
    }

    @Test
    void save_validLocation_savesSuccessfully() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.of(cityModel));

        assertDoesNotThrow(() -> locationUseCase.save(locationModel));

        verify(locationPersistencePort).save(locationModel);
        assertSame(cityModel, locationModel.getCity());
    }

    @Test
    void save_invalidCity_throwsException() {
        when(cityUseCase.getById(1L)).thenReturn(Optional.empty());

        EmptyException ex = assertThrows(
                EmptyException.class,
                () -> locationUseCase.save(locationModel)
        );
        assertEquals(DomainConstants.DEPARTMENT_DOES_NOT_EXIST, ex.getMessage());

        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void getFilters_DelegatesToPersistencePort() {
        int page = 0, size = 10;
        Long idCity = 1L, idDepartment = 2L;
        String search = "Zona";
        boolean orderAsc = true;

        PagedResult<LocationModel> expected = new PagedResult<>(
                List.of(locationModel),
                1,   // totalElements as long
                1,    // totalPages as int
                10    // size as int
        );
        when(locationPersistencePort.getFilters(page, size, idCity, idDepartment, search, orderAsc))
                .thenReturn(expected);

        PagedResult<LocationModel> result = locationUseCase.getFilters(
                page, size, idCity, idDepartment, search, orderAsc
        );

        assertEquals(expected, result);
        verify(locationPersistencePort).getFilters(page, size, idCity, idDepartment, search, orderAsc);
    }

    @Test
    void getById_WhenExists_ReturnsLocationModel() {
        Long id = 1L;
        LocationModel expected = new LocationModel(id, "Zona Norte", 3L, cityModel);
        when(locationPersistencePort.findById(id)).thenReturn(Optional.of(expected));

        LocationModel result = locationUseCase.getById(id);

        assertEquals(expected, result);
        verify(locationPersistencePort).findById(id);
    }

    @Test
    void getById_WhenNotFound_ThrowsEntityNotFoundException() {
        Long id = 99L;
        when(locationPersistencePort.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> locationUseCase.getById(id)
        );
        assertEquals(DomainConstants.LOCATION_DOES_NOT_EXIST, ex.getMessage());
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
    void findByCityName_ReturnsIdIfExists() {
        String cityName = "Bucaramanga";
        LocationModel model = new LocationModel(2L, "Ruitoque", 5L, cityModel);

        when(locationPersistencePort.findByCityName(cityName)).thenReturn(Optional.of(model));

        Optional<Long> result = locationUseCase.findByCityName(cityName);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get());
    }

    @Test
    void findByCityName_WhenNotFound_ReturnsEmpty() {
        when(locationPersistencePort.findByCityName("Nada")).thenReturn(Optional.empty());

        Optional<Long> result = locationUseCase.findByCityName("Nada");

        assertTrue(result.isEmpty());
    }

    @Test
    void getIdByDepartmentName_ReturnsFirstMatchingId() {
        String departmentName = "Santander";
        List<LocationModel> models = List.of(
                new LocationModel(3L, "Cabecera", 2L, cityModel),
                new LocationModel(4L, "Centro", 2L, cityModel)
        );

        when(locationPersistencePort.findByDepartmentName(departmentName)).thenReturn(models);

        Optional<Long> result = locationUseCase.getIdByDepartmentName(departmentName);

        assertTrue(result.isPresent());
        assertEquals(3L, result.get());
    }

    @Test
    void getIdByDepartmentName_WhenNotFound_ReturnsEmpty() {
        when(locationPersistencePort.findByDepartmentName("Nada")).thenReturn(List.of());

        Optional<Long> result = locationUseCase.getIdByDepartmentName("Nada");

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllIdsByDepartmentName_ReturnsAllMatchingIds() {
        String departmentName = "Santander";
        List<LocationModel> models = List.of(
                new LocationModel(3L, "Cabecera", 2L, cityModel),
                new LocationModel(4L, "Centro", 2L, cityModel)
        );

        when(locationPersistencePort.findByDepartmentName(departmentName)).thenReturn(models);

        List<Long> ids = locationUseCase.getAllIdsByDepartmentName(departmentName);

        assertEquals(List.of(3L, 4L), ids);
    }

    @Test
    void getNameById_ReturnsName_WhenExists() {
        Long id = 5L;
        LocationModel model = new LocationModel(id, "Ruitoque", 2L, cityModel);

        when(locationPersistencePort.findById(id)).thenReturn(Optional.of(model));

        String name = locationUseCase.getNameById(id);

        assertEquals("Ruitoque", name);
        verify(locationPersistencePort).findById(id);
    }

    @Test
    void findById_DelegatesToPersistencePort() {
        Long id = 7L;
        LocationModel model = new LocationModel(id, "Centro", 3L, cityModel);

        when(locationPersistencePort.findById(id)).thenReturn(Optional.of(model));

        Optional<LocationModel> result = locationUseCase.findById(id);

        assertTrue(result.isPresent());
        assertEquals(model, result.get());
        verify(locationPersistencePort).findById(id);
    }
}


