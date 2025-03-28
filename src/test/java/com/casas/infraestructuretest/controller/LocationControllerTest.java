package com.casas.infraestructuretest.controller;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.endpoints.rest.LocationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        SaveLocationRequest request = new SaveLocationRequest("Santander", 1L, "Bucaramanga");
        SaveLocationResponse response = new SaveLocationResponse("Location created successfully", LocalDateTime.now());

        when(locationService.save(request)).thenReturn(response);

        ResponseEntity<SaveLocationResponse> result = locationController.save(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetAllLocationsFilters() {
        LocationResponse location1 = new LocationResponse(1L, "Santander", null);
        LocationResponse location2 = new LocationResponse(2L, "Bogotá", null);
        PagedResult<LocationResponse> pagedResult = new PagedResult<>(List.of(location1, location2), 0, 10, 2L);

        when(locationService.getAllLocationsFilters(0, 10, null, true)).thenReturn(pagedResult);

        ResponseEntity<PagedResult<LocationResponse>> result = locationController.getAllLocationsFilters(0, 10, null, true);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pagedResult, result.getBody());
    }
}

