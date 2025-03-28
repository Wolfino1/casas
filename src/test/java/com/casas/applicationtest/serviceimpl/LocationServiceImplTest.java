package com.casas.applicationtest.serviceimpl;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.LocationDtoMapper;
import com.casas.casas.application.mappers.PageMapperApplication;
import com.casas.casas.application.services.impl.LocationServiceImpl;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationServicePort locationServicePort;

    @Mock
    private LocationDtoMapper locationDtoMapper;

    @Mock
    private PageMapperApplication pageMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private SaveLocationRequest saveLocationRequest;
    private LocationModel locationModel;
    private SaveLocationResponse saveLocationResponse;
    private LocationResponse locationResponse;
    private PagedResult<LocationModel> pagedLocationModel;
    private PagedResult<LocationResponse> pagedLocationResponse;

    @BeforeEach
    void setUp() {
        saveLocationRequest = new SaveLocationRequest("Test Location", 1L,"Name City");
        locationModel = new LocationModel(1L, "Test Location", 1L, null);
        locationResponse = new LocationResponse(1L, "Test Location", null);
        saveLocationResponse = new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
        pagedLocationModel = new PagedResult<>(List.of(locationModel), 0, 10, 1);
        pagedLocationResponse = new PagedResult<>(List.of(locationResponse), 0, 10, 1);
    }

    @Test
    void save_ShouldSaveLocationAndReturnResponse() {
        when(locationDtoMapper.requestToModel(saveLocationRequest)).thenReturn(locationModel);
        doNothing().when(locationServicePort).save(locationModel);

        SaveLocationResponse response = locationService.save(saveLocationRequest);

        assertNotNull(response);
        assertEquals(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, response.message());
        verify(locationServicePort, times(1)).save(locationModel);
    }

    @Test
    void getAllLocationsFilters_ShouldReturnPagedResult() {
        when(locationServicePort.getFilters(0, 10, 1L, true)).thenReturn(pagedLocationModel);
        when(locationDtoMapper.modelToResponse(locationModel)).thenReturn(locationResponse);
        when(pageMapper.fromPage(Collections.singletonList(locationResponse), pagedLocationModel)).thenReturn(pagedLocationResponse);

        PagedResult<LocationResponse> result = locationService.getAllLocationsFilters(0, 10, 1L, true);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Location", result.getContent().get(0).name());
        verify(locationServicePort, times(1)).getFilters(0, 10, 1L, true);
    }
}