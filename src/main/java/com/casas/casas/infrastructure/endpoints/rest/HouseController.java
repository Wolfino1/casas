package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveHouseRequest;
import com.casas.casas.application.dto.response.HouseResponse;
import com.casas.casas.application.dto.response.SaveHouseResponse;
import com.casas.casas.application.dto.response.SellerHouseResponse;
import com.casas.casas.application.mappers.HouseDtoMapper;
import com.casas.casas.application.services.HouseService;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@Slf4j

@RestController
@RequestMapping("/api/v1/house")
@RequiredArgsConstructor
@Tag(name= "House", description = "Controller for Houses")
public class HouseController {
    private final HouseService houseService;
    private final JwtUtil jwtUtil;
    private final HouseDtoMapper mapper;

    @PreAuthorize("hasRole('SELLER')")
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
    public ResponseEntity<SaveHouseResponse> save(@RequestBody SaveHouseRequest req) {
        // 1. Tomo los Claims que puse como "principal" en el filter
        Claims claims = (Claims) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        // 2. Extraigo el sellerId del claim (está en el payload del token)
        Long sellerId = claims.get("sellerId", Long.class);

        // 3. Convierto el request a HouseModel (sin sellerId aún)
        HouseModel model = mapper.requestToModel(req);

        // 4. Le asigno el sellerId que saqué del token
        model.setSellerId(sellerId);

        // 5. Llamo al service
        SaveHouseResponse resp = houseService.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/filters")
    public ResponseEntity<PagedResult<HouseResponse>> getHouseFiltered(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer numberOfRooms,
            @RequestParam(required = false) Integer numberOfBathrooms,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam boolean orderAsc) {

        return ResponseEntity.ok(houseService.getHouseFiltered(
                page, size, category, name, numberOfRooms, numberOfBathrooms, minPrice, maxPrice, location, sortBy ,orderAsc));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get house by ID", description = "Returns a single house")
    public ResponseEntity<HouseResponse> getById(@PathVariable Long id) {
        HouseResponse house = houseService.getById(id);
        return ResponseEntity.ok(house);
    }

    @GetMapping("/seller/filters")
    public ResponseEntity<PagedResult<SellerHouseResponse>> getHousesBySeller(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "true") boolean orderAsc
    ) {
        Claims claims = (Claims) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Long sellerId = claims.get("sellerId", Integer.class).longValue();

        PagedResult<SellerHouseResponse> result =
                houseService.getHousesBySellerFiltered(
                        sellerId, page, size, id, name, category, minPrice, location, orderAsc
                );
        return ResponseEntity.ok(result);
    }

}