package com.casas.domaintest.usecase;


import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
        when(categoryPersistencePort.getByName(category.getName())).thenReturn(null);

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
        Page<CategoryModel> page = new PageImpl<>(List.of(category));
        when(categoryPersistencePort.get(anyInt(), anyInt(), anyBoolean())).thenReturn(page);

        Page<CategoryModel> result = categoryUseCase.get(0, 10, true);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(categoryPersistencePort, times(1)).get(0, 10, true);
    }
}
