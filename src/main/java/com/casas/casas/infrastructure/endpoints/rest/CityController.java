package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveCityRequest;
import com.casas.casas.application.dto.response.SaveLocationResponse;
import com.casas.casas.application.services.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
@Tag(name= "City", description = "Controller for City")
public class CityController {

    private final CityService cityService;

    @PostMapping("")
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveCityRequest saveCityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(saveCityRequest));
    }
}
