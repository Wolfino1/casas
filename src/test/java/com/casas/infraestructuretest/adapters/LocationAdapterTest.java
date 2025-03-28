package com.casas.infraestructuretest.adapters;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.infrastructure.adapters.persistence.mysql.LocationPersistenceAdapter;
import com.casas.casas.infrastructure.entities.LocationEntity;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.mappers.PageMapperInfra;
import com.casas.casas.infrastructure.repositories.mysql.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationAdapterTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationEntityMapper locationEntityMapper;

    @Mock
    private PageMapperInfra pageMapperInfra;

    @InjectMocks
    private LocationPersistenceAdapter locationPersistenceAdapter;

    private LocationModel locationModel;
    private LocationEntity locationEntity;
    private CityModel cityModel;

    @BeforeEach
    void setUp() {
        cityModel = new CityModel(1L, "Bucaramanga", "Ciudad Bonita", 1L, null);
        locationModel = new LocationModel(1L, "Cabecera", 1L, cityModel);
        locationEntity = new LocationEntity(1L, "Cabecera", null);
    }

    @Test
    void save_ShouldSaveLocation() {
        when(locationEntityMapper.toEntity(locationModel, locationModel.getCity())).thenReturn(locationEntity);

        locationPersistenceAdapter.save(locationModel);

        verify(locationRepository, times(1)).save(locationEntity);
    }

    @Test
    void getFilters_ShouldReturnPagedLocations() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<LocationEntity> page = new PageImpl<>(List.of(locationEntity));
        when(locationRepository.findByCityId(1L, pageable)).thenReturn(page);
        when(locationEntityMapper.entityToModel(locationEntity)).thenReturn(locationModel);
        when(pageMapperInfra.fromPage(any())).thenCallRealMethod();

        var result = locationPersistenceAdapter.getFilters(0, 10, 1L, true);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
        verify(locationRepository, times(1)).findByCityId(1L, pageable);
    }
}