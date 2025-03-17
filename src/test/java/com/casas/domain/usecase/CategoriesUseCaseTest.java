package com.casas.domain.usecase;

import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CategoriesUseCaseTest {

    @InjectMocks
    private CategoryUseCase categoryUseCase;
    @Mock
    private CategoryPersistencePort categoryPersistencePort;
    @Test
    void saveTest (){
        when(categoryPersistencePort.getByName(any())).thenReturn(null);
        categoryUseCase.save(CategoryModel.builder().name("Case").description("description").build());
    }
    @Test
    void saveTestNull (){
        when(categoryPersistencePort.getByName(any())).thenReturn(CategoryModel.builder().id(1L).name("Case").description("description").build());
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.save(CategoryModel.builder().name("Case").description("description").build()));

    }
    @Test
    void get(){
        when(categoryPersistencePort.get(any(),any(),anyBoolean())).thenReturn(List.of(CategoryModel.builder().id(1L).name("Case").description("description").build()));
        assertEquals(1L,categoryUseCase.get(1,1,true).get(0).getId());
    }
    @Test
    void getFilters(){
        when(categoryPersistencePort.getFilters(any(),any(),anyString(),anyString(),anyBoolean())).thenReturn(List.of(CategoryModel.builder().id(1L).name("Case").description("description").build()));
        assertEquals(1L,categoryUseCase.getFilters(1,1,"m","o",true).get(0).getId());
    }
}
