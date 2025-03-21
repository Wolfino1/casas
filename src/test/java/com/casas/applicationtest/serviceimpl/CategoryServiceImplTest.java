package com.casas.applicationtest.serviceimpl;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.mappers.CategoryDtoMapper;
import com.casas.casas.application.services.impl.CategoryServiceImpl;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.common.configurations.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryServicePort categoryServicePort;

    @Mock
    private CategoryDtoMapper categoryDtoMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private SaveCategoryRequest saveCategoryRequest;
    private CategoryModel categoryModel;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        saveCategoryRequest = new SaveCategoryRequest("Test Category", "Test Description");
        categoryModel = new CategoryModel(1L, "Test Category", "Test Description");
        categoryResponse = new CategoryResponse(1L, "Test Category", "Test Description");
    }

    @Test
    void save_ShouldSaveCategoryAndReturnResponse() {
        when(categoryDtoMapper.requestToModel(saveCategoryRequest)).thenReturn(categoryModel);

        SaveCategoryResponse response = categoryService.save(saveCategoryRequest);

        assertNotNull(response);
        assertEquals(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, response.message());
        verify(categoryServicePort, times(1)).save(categoryModel);
    }

    @Test
    void getCategories_ShouldReturnPagedCategories() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CategoryModel> categoryPage = new PageImpl<>(List.of(categoryModel), pageRequest, 1);

        when(categoryServicePort.get(0, 10, true)).thenReturn(categoryPage);
        when(categoryDtoMapper.modelToResponse(categoryModel)).thenReturn(categoryResponse);

        Page<CategoryResponse> result = categoryService.getCategories(0, 10, true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(categoryResponse, result.getContent().get(0));
        verify(categoryServicePort, times(1)).get(0, 10, true);
    }
}


