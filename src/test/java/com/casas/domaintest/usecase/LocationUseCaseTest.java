package com.casas.domaintest.usecase;


import com.casas.casas.domain.exceptions.LocationAlreadyExistsException;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.usecases.LocationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseTest {

    @Mock
    private LocationPersistencePort locationPersistencePort;

    @InjectMocks
    private LocationUseCase locationUseCase;
    private LocationModel location;

    @BeforeEach
    void setUp() {
        location = new LocationModel(1L, "Tienda Principal", "Bucaramanga", "Centro de la ciudad", "Santander", "Departamento del oriente colombiano");
    }

    @Test
    void save_ShouldSaveLocation_WhenLocationDoesNotExist() {
        when(locationPersistencePort.getByCityAndDepartment(location.getCity(), location.getDepartment()))
                .thenReturn(Optional.empty());

        locationUseCase.save(location);

        verify(locationPersistencePort, times(1)).save(location);
    }

    @Test
    void save_ShouldThrowException_WhenLocationAlreadyExists() {
        when(locationPersistencePort.getByCityAndDepartment(location.getCity(), location.getDepartment()))
                .thenReturn(Optional.of(location));

        assertThrows(LocationAlreadyExistsException.class, () -> locationUseCase.save(location));
    }

    @Test
    void getFilters_ShouldReturnPagedLocations() {
        Page<LocationModel> page = new PageImpl<>(List.of(location));
        when(locationPersistencePort.getFilters(anyInt(), anyInt(), anyString(), anyString(), anyBoolean()))
                .thenReturn(page);

        Page<LocationModel> result = locationUseCase.getFilters(0, 10, "Bucaramanga", "Santander", true);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(locationPersistencePort, times(1)).getFilters(0, 10, "Bucaramanga", "Santander", true);
    }
}
