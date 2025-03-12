package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveHomeRequest;
import com.casas.casas.application.dto.response.HomeResponse;
import com.casas.casas.application.dto.response.SaveHomeResponse;
import com.casas.casas.application.services.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @PostMapping("/")
    public ResponseEntity<SaveHomeResponse> save(@RequestBody SaveHomeRequest saveHomeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(homeService.save(saveHomeRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<HomeResponse>> getAllHome(@RequestParam Integer page, @RequestParam Integer size,
                                                         @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(homeService.getHomes(page, size, orderAsc));
    }
}