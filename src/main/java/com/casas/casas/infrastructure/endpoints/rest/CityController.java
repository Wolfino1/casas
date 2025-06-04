package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.CityResponse;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.mappers.CityDtoMapper;
import com.casas.casas.application.services.CityService;
import com.casas.casas.domain.model.CityModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
@Tag(name= "City", description = "Controller for City")
public class CityController {

    private final CityService cityService;
    private final CityDtoMapper cityDtoMapper;

    @PostMapping("")
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveCityRequest saveCityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(saveCityRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<CityResponse> response = cityService.getAllCities()
                .stream()
                .map(cityDtoMapper::modelToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) {
        CityModel model = cityService.getCityById(id);
        CityResponse response = cityDtoMapper.modelToResponse(model);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<CityResponse>> getCitiesByDepartment(@PathVariable Long departmentId) {
        List<CityResponse> response = cityService.getCitiesByDepartmentId(departmentId)
                .stream()
                .map(cityDtoMapper::modelToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


}
