package com.casas.casas.infrastructure.endpoints.rest;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.services.CategoryService;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
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
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name= "Category", description = "Controller for Categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @Operation(summary = "Create category", description = "This method saves an nonexistent category", tags =
            {"Category"}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description =
            "Creates a new Category", required = true, content = @Content(mediaType = "application/jason",
            schema = @Schema (implementation = SaveCategoryRequest.class))), responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Category created successfully",
                    content = @Content(mediaType = "application/jason",
                            schema = @Schema (implementation = SaveCategoryResponse.class))
                                )})
    public ResponseEntity<SaveCategoryResponse> save(@RequestBody SaveCategoryRequest saveCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(saveCategoryRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<PagedResult<CategoryResponse>> getAllCategories(@RequestParam Integer page, @RequestParam Integer size,
                                                                          @RequestParam boolean orderAsc) {
        return ResponseEntity.ok(categoryService.getCategories(page, size, orderAsc));
    }

}