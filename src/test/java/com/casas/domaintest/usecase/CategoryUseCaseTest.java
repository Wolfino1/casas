package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.exceptions.NullException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import jakarta.persistence.EntityNotFoundException;
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

    private CategoryModel baseCategory;

    @BeforeEach
    void setUp() {
        baseCategory = new CategoryModel(1L, "Residencial", "Descripción");
    }

    @Test
    void save_WhenNameAlreadyExists_ThrowsCategoryAlreadyExistsException() {
        when(categoryPersistencePort.getByName("Residencial"))
                .thenReturn(Optional.of(baseCategory));

        assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryUseCase.save(baseCategory)
        );
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_WhenNameDoesNotExist_SavesSuccessfully() {
        when(categoryPersistencePort.getByName("Residencial"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> categoryUseCase.save(baseCategory));
        verify(categoryPersistencePort).save(baseCategory);
    }

    @Test
    void get_DelegatesToPersistencePort() {
        PagedResult<CategoryModel> expected = new PagedResult<>(List.of(baseCategory), 1, 1, 10);
        when(categoryPersistencePort.get(0, 10, true)).thenReturn(expected);

        PagedResult<CategoryModel> result = categoryUseCase.get(0, 10, true);

        assertEquals(expected, result);
        verify(categoryPersistencePort).get(0, 10, true);
    }

    @Test
    void getById_WhenExists_ReturnsCategoryModel() {
        when(categoryPersistencePort.findById(1L)).thenReturn(Optional.of(baseCategory));

        CategoryModel result = categoryUseCase.getById(1L);

        assertEquals(baseCategory, result);
        verify(categoryPersistencePort).findById(1L);
    }

    @Test
    void getById_WhenNotFound_ThrowsEntityNotFoundException() {
        when(categoryPersistencePort.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> categoryUseCase.getById(99L)
        );
        assertEquals(DomainConstants.CATEGORY_DOES_NOT_EXIST, ex.getMessage());
    }

    @Test
    void getIdByName_WhenExists_ReturnsId() {
        when(categoryPersistencePort.findByName("Residencial"))
                .thenReturn(Optional.of(baseCategory));

        Long id = categoryUseCase.getIdByName("Residencial");

        assertEquals(1L, id);
    }

    @Test
    void getNameById_ReturnsName_WhenExists() {
        when(categoryPersistencePort.findById(1L)).thenReturn(Optional.of(baseCategory));

        String name = categoryUseCase.getNameById(1L);

        assertEquals("Residencial", name);
        verify(categoryPersistencePort).findById(1L);
    }
}
