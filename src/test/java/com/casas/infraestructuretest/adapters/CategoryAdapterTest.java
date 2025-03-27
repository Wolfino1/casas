package com.casas.infraestructuretest.adapters;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.infrastructure.adapters.persistence.mysql.CategoryPersistenceAdapter;
import com.casas.casas.infrastructure.mappers.CategoryEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
import com.casas.casas.infrastructure.entities.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryAdapterTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryPersistenceAdapter categoryPersistenceAdapter;

    private CategoryModel categoryModel;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        categoryModel = new CategoryModel(1L, "Anime", "Categoría de figuras de anime");
        categoryEntity = new CategoryEntity(1L, "Anime", "Categoría de figuras de anime");
    }

    @Test
    void save_ShouldSaveCategory() {
        when(categoryEntityMapper.modelToEntity(categoryModel)).thenReturn(categoryEntity);

        categoryPersistenceAdapter.save(categoryModel);

        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    void getByName_ShouldReturnCategory_WhenExists() {
        when(categoryRepository.findByName("Anime")).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.entityToModel(categoryEntity)).thenReturn(categoryModel);

        Optional<CategoryModel> result = categoryPersistenceAdapter.getByName("Anime");

        assertTrue(result.isPresent());
        assertEquals("Anime", result.get().getName());
    }

    @Test
    void getByName_ShouldReturnEmptyOptional_WhenNotExists() {
        when(categoryRepository.findByName("Anime")).thenReturn(Optional.empty());

        Optional<CategoryModel> result = categoryPersistenceAdapter.getByName("Anime");

        assertTrue(result.isEmpty());
    }

    @Test
    void get_ShouldReturnPagedCategories() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<CategoryEntity> page = new PageImpl<>(List.of(categoryEntity));
        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryEntityMapper.entityToModel(categoryEntity)).thenReturn(categoryModel);

        Page<CategoryModel> result = categoryPersistenceAdapter.get(0, 10, true);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(categoryRepository, times(1)).findAll(pageable);
    }
}