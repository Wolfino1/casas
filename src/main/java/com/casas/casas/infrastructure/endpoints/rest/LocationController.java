package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/")
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveLocationRequest savelocationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(savelocationRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<LocationResponse>> getAlllocation(@RequestParam Integer page, @RequestParam Integer size,
                                                             @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(locationService.getLocations(page, size, orderAsc));
    }
    @GetMapping("/filters")
    public ResponseEntity<List<LocationResponse>> getAllLocationsFilters(@RequestParam Integer page,
                                                                          @RequestParam Integer size,
                                                                          @RequestParam(required = false) String city,
                                                                          @RequestParam(required = false) String department,
                                                                          @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(locationService.getAllLocationsFilters(page, size, city, department, orderAsc));
    }
}