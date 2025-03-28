package com.casas.domaintest.usecase;


import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.utils.page.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private CategoryModel category;

    @BeforeEach
    void setUp() {
        category = new CategoryModel(1L, "Figuras", "Figuras coleccionables");
    }

    @Test
    void save_ShouldSaveCategory_WhenCategoryDoesNotExist() {
        when(categoryPersistencePort.getByName(category.getName())).thenReturn(Optional.empty()); // Cambio aquí

        categoryUseCase.save(category);

        verify(categoryPersistencePort, times(1)).save(category);
    }

    @Test
    void save_ShouldThrowException_WhenCategoryAlreadyExists() {
        when(categoryPersistencePort.getByName(category.getName())).thenReturn(Optional.of(category));

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.save(category));
    }

    @Test
    void get_ShouldReturnPagedCategories() {

        CategoryModel category = new CategoryModel(1L, "Category Name", "Description");
        PagedResult<CategoryModel> pagedResult = new PagedResult<>(List.of(category), 1, 0, 10);

        when(categoryPersistencePort.get(anyInt(), anyInt(), anyBoolean())).thenReturn(pagedResult);

        PagedResult<CategoryModel> result = categoryUseCase.get(0, 10, true);

        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(category.getId(), result.getContent().get(0).getId());
    }
}
