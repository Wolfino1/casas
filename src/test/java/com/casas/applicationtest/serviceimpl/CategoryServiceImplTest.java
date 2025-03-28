package com.casas.applicationtest.serviceimpl;

import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.mappers.CategoryDtoMapper;
import com.casas.casas.application.mappers.PageMapperApplication;
import com.casas.casas.application.services.impl.CategoryServiceImpl;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.in.CategoryServicePort;

import com.casas.casas.application.dto.request.SaveCategoryRequest;

import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryServicePort categoryServicePort;

    @Mock
    private CategoryDtoMapper categoryDtoMapper;

    @Mock
    private PageMapperApplication pageMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private SaveCategoryRequest saveCategoryRequest;
    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        saveCategoryRequest = new SaveCategoryRequest("Juguetes", "Categoría de juguetes");
        categoryModel = new CategoryModel(1L, "Juguetes", "Categoría de juguetes");
    }

    @Test
    void save_ShouldSaveCategoryAndReturnResponse() {
        when(categoryDtoMapper.requestToModel(saveCategoryRequest)).thenReturn(categoryModel);
        doNothing().when(categoryServicePort).save(any(CategoryModel.class)); // 🔥 Ajuste aquí

        SaveCategoryResponse response = categoryService.save(saveCategoryRequest);

        assertNotNull(response);
        assertEquals(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, response.message());
        verify(categoryServicePort, times(1)).save(any(CategoryModel.class)); // 🔥 Ajuste aquí
    }

    @Test
    void getCategories_ShouldReturnPagedCategories() {
        PagedResult<CategoryModel> categoryModelPagedResult = new PagedResult<>(
                List.of(categoryModel), 1, 1, 1);

        when(categoryServicePort.get(0, 10, true)).thenReturn(categoryModelPagedResult); // 🔥 No será null
        when(categoryDtoMapper.modelToResponse(categoryModel)).thenReturn(
                new CategoryResponse(1L, "Juguetes", "Categoría de juguetes"));

        when(pageMapper.fromPage(anyList(), any())).thenReturn(new PagedResult<>(
                List.of(new CategoryResponse(1L, "Juguetes", "Categoría de juguetes")), 1, 1, 1));

        PagedResult<CategoryResponse> result = categoryService.getCategories(0, 10, true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(categoryServicePort, times(1)).get(0, 10, true);
    }

}


