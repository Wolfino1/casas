package com.casas.applicationtest.serviceimpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.LocationDtoMapper;
import com.casas.casas.application.services.impl.LocationServiceImpl;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.common.configurations.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationServicePort locationServicePort;

    @Mock
    private LocationDtoMapper locationDtoMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private SaveLocationRequest saveLocationRequest;
    private LocationModel locationModel;
    private LocationResponse locationResponse;

    @BeforeEach
    void setUp() {
        saveLocationRequest = new SaveLocationRequest("Lugar X", "Bucaramanga", "Ciudad bonita", "Santander", "Departamento histórico");
        locationModel = new LocationModel(1L, "Bucaramanga", "Bucaramanga", "Ciudad bonita", "Santander", "Departamento histórico");
        locationResponse = new LocationResponse(1L, "Bucaramanga", "Bucaramanga", "Ciudad bonita", "Santander", "Departamento histórico");

    }

    @Test
    void save_ShouldReturnSuccessResponse() {
        when(locationDtoMapper.requestToModel(saveLocationRequest)).thenReturn(locationModel);
        doNothing().when(locationServicePort).save(locationModel);

        SaveLocationResponse response = locationService.save(saveLocationRequest);

        assertNotNull(response);
        assertEquals(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, response.message());
        assertNotNull(response.time());
        verify(locationServicePort, times(1)).save(locationModel);
    }

    @Test
    void getAllLocationsFilters_ShouldReturnPagedResults() {
        int page = 0, size = 5;
        boolean orderAsc = true;
        Page<LocationModel> pagedLocations = new PageImpl<>(List.of(locationModel), PageRequest.of(page, size), 1);

        when(locationServicePort.getFilters(page, size, "Bucaramanga", "Santander", orderAsc)).thenReturn(pagedLocations);
        when(locationDtoMapper.modelToResponse(locationModel)).thenReturn(locationResponse);

        Page<LocationResponse> result = locationService.getAllLocationsFilters(page, size, "Bucaramanga", "Santander", orderAsc);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Bucaramanga", result.getContent().get(0).city());
        verify(locationServicePort, times(1)).getFilters(page, size, "Bucaramanga", "Santander", orderAsc);
    }
}