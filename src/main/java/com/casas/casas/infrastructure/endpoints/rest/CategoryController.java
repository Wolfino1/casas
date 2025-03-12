package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<SaveCategoryResponse> save(@RequestBody SaveCategoryRequest saveCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(saveCategoryRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam Integer page, @RequestParam Integer size,
                                                                   @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(categoryService.getCategories(page, size, orderAsc));
    }
    @GetMapping("/filters")
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesFilters(@RequestParam Integer page,
                                                                          @RequestParam Integer size,
                                                                          @RequestParam(required = false) String name,
                                                                          @RequestParam(required = false) String description,
                                                                          @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(categoryService.getAllCategoriesFilters(page, size, name, description, orderAsc));
    }
}