package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveHouseRequest;
import com.casas.casas.application.dto.response.HouseResponse;
import com.casas.casas.application.dto.response.SaveHouseResponse;
import com.casas.casas.application.services.HouseService;
import com.casas.casas.domain.utils.page.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/house")
@RequiredArgsConstructor
@Tag(name= "House", description = "Controller for Houses")
public class HouseController {
    private final HouseService houseService;

    @PostMapping("/")
    @Operation(summary = "Create house", description = "This method saves a house", tags =
            {"House"}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description =
            "Creates a new House", required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SaveHouseRequest.class))), responses = {@ApiResponse(
            responseCode = "200",
            description = "House created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SaveHouseResponse.class))
    )})
    public ResponseEntity<SaveHouseResponse> save(@RequestBody SaveHouseRequest saveHouseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.save(saveHouseRequest));
    }

    @GetMapping("/filters")
    public ResponseEntity<PagedResult<HouseResponse>> getHouseFiltered(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer numberOfRooms,
            @RequestParam(required = false) Integer numberOfBathrooms,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam boolean orderAsc) {

        return ResponseEntity.ok(houseService.getHouseFiltered(
                page, size, category, numberOfRooms, numberOfBathrooms, minPrice, maxPrice, location, sortBy ,orderAsc));
    }
}