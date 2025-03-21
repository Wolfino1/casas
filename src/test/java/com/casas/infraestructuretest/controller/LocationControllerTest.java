package com.casas.infraestructuretest.controller;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.infrastructure.endpoints.rest.LocationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveLocation() {
        SaveLocationRequest request = new SaveLocationRequest("Bucaramanga", "Santander", "Descripción Ciudad", "Santander", "Descripción Departamento");
        SaveLocationResponse response = new SaveLocationResponse("Location saved successfully", LocalDateTime.now());

        when(locationService.save(request)).thenReturn(response);

        ResponseEntity<SaveLocationResponse> result = locationController.save(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(locationService, times(1)).save(request);
    }

    @Test
    void testGetAllLocationsFilters() {
        int page = 0, size = 5;
        boolean orderAsc = true;
        String city = "Bucaramanga";
        String department = "Santander";

        List<LocationResponse> locations = List.of(
                new LocationResponse(1L, "Bucaramanga", "Santander", "Descripción Ciudad", "Santander", "Descripción Departamento"),
                new LocationResponse(2L, "Floridablanca", "Santander", "Descripción Ciudad", "Santander", "Descripción Departamento")
        );
        Page<LocationResponse> pagedResponse = new PageImpl<>(locations);

        when(locationService.getAllLocationsFilters(page, size, city, department, orderAsc)).thenReturn(pagedResponse);

        ResponseEntity<Page<LocationResponse>> result = locationController.getAllLocationsFilters(page, size, city, department, orderAsc);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pagedResponse, result.getBody());
        verify(locationService, times(1)).getAllLocationsFilters(page, size, city, department, orderAsc);
    }
}

