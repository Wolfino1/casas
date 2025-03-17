package com.casas.domain.usecase;

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

import static org.mockito.ArgumentMatchers.any;
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
}
