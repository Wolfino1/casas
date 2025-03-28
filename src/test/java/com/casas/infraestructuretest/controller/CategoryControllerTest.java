package com.casas.infraestructuretest.controller;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.services.CategoryService;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.endpoints.rest.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory() {
        SaveCategoryRequest request = new SaveCategoryRequest("Anime", "Figuras de anime");
        SaveCategoryResponse response = new SaveCategoryResponse("Categoría guardada exitosamente", null);

        when(categoryService.save(request)).thenReturn(response);

        ResponseEntity<SaveCategoryResponse> result = categoryController.save(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetAllCategories() {
        CategoryResponse category1 = new CategoryResponse(1L, "Anime", "Figuras de anime");
        CategoryResponse category2 = new CategoryResponse(2L, "Películas", "Figuras de películas");
        PagedResult<CategoryResponse> pagedResult = new PagedResult<>(List.of(category1, category2), 0, 10, 2);

        when(categoryService.getCategories(0, 10, true)).thenReturn(pagedResult);

        ResponseEntity<PagedResult<CategoryResponse>> result = categoryController.getAllCategories(0, 10, true);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pagedResult, result.getBody());
    }

}
