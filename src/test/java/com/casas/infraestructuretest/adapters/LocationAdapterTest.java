package com.casas.infraestructuretest.adapters;

import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.infrastructure.adapters.persistence.mysql.LocationPersistenceAdapter;
import com.casas.casas.infrastructure.entities.LocationEntity;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationPersistenceAdapterTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationEntityMapper locationEntityMapper;

    @InjectMocks
    private LocationPersistenceAdapter locationPersistenceAdapter;

    private LocationModel locationModel;
    private LocationEntity locationEntity;

    @BeforeEach
    void setUp() {
        locationModel = new LocationModel(1L, "Bucaramanga", "Santander");
        locationEntity = new LocationEntity();
        locationEntity.setId(1L);
        locationEntity.setCity("Bucaramanga");
        locationEntity.setDepartment("Santander");
    }

    @Test
    void save_ShouldSaveLocation() {
        when(locationEntityMapper.modelToEntity(any(LocationModel.class))).thenReturn(locationEntity);
        when(locationRepository.save(any(LocationEntity.class))).thenReturn(locationEntity);

        locationPersistenceAdapter.save(locationModel);

        verify(locationRepository, times(1)).save(locationEntity);
    }

    @Test
    void getByCityAndDepartment_ShouldReturnLocation_WhenExists() {
        when(locationRepository.findByCityAndDepartment(anyString(), anyString()))
                .thenReturn(Optional.of(locationEntity));

        when(locationEntityMapper.entityToModel(locationEntity)) // Simulación del mapper
                .thenReturn(locationModel);

        Optional<LocationModel> result = locationPersistenceAdapter.getByCityAndDepartment("Bucaramanga", "Santander");

        assertTrue(result.isPresent());
        assertEquals("Bucaramanga", result.get().getCity());
        assertEquals("Santander", result.get().getDepartment());
    }

    @Test
    void getByCityAndDepartment_ShouldReturnEmpty_WhenNotExists() {
        when(locationRepository.findByCityAndDepartment(anyString(), anyString()))
                .thenReturn(Optional.empty());

        Optional<LocationModel> result = locationPersistenceAdapter.getByCityAndDepartment("Bogotá", "Cundinamarca");

        assertFalse(result.isPresent());
    }

    @Test
    void getFilters_ShouldReturnPagedLocations() {
        Page<LocationEntity> locationEntitiesPage = new PageImpl<>(List.of(locationEntity));
        when(locationRepository.findAll(any(Pageable.class))).thenReturn(locationEntitiesPage);
        when(locationEntityMapper.entityToModel(any(LocationEntity.class))).thenReturn(locationModel);

        Page<LocationModel> result = locationPersistenceAdapter.getFilters(0, 10, "", "", true);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals("Bucaramanga", result.getContent().get(0).getCity());
    }
}