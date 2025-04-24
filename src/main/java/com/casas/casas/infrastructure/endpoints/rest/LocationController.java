package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveLocationRequest;
import com.casas.casas.application.dto.response.LocationResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.services.LocationService;
import com.casas.casas.domain.utils.page.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Tag(name= "Location", description = "Controller for Locations")

public class LocationController {
    private final LocationService locationService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create Location", description = "This method saves an nonexistent location", tags =
            {"Location"}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description =
            "Creates a new Location", required = true, content = @Content(mediaType = "application/jason",
            schema = @Schema(implementation = SaveLocationResponse.class))), responses = {@ApiResponse(
                responseCode = "200",
                description = "Location created successfully",
                content = @Content(mediaType = "application/jason",
                     schema = @Schema (implementation = SaveLocationResponse.class))
                        )})
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveLocationRequest savelocationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(savelocationRequest));
    }

    @GetMapping("/filters")
    public ResponseEntity<PagedResult<LocationResponse>> getAllLocationsFilters(@RequestParam Integer page,
                                                                                @RequestParam Integer size,
                                                                                @RequestParam(required = false) Long idCity,
                                                                                @RequestParam(required = false) Long idDepartment,
                                                                                @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(locationService.getAllLocationsFilters(page, size, idCity, idDepartment, orderAsc));
    }
}