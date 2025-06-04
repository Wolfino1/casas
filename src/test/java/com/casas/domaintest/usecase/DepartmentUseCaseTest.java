package com.casas.domaintest.usecase;

import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;
import com.casas.casas.domain.usecases.DepartmentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentUseCaseTest {

    @Mock
    private DepartmentPersistencePort departmentPersistencePort;

    @InjectMocks
    private DepartmentUseCase departmentUseCase;

    private DepartmentModel departmentModel;

    @BeforeEach
    void setUp() {
        departmentModel = new DepartmentModel(1L, "Santander",":D");
    }

    @Test
    void getById_WhenExists_ReturnsOptional() {
        when(departmentPersistencePort.getByDepartmentById(1L)).thenReturn(Optional.of(departmentModel));

        Optional<DepartmentModel> result = departmentUseCase.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(departmentModel, result.get());
        verify(departmentPersistencePort).getByDepartmentById(1L);
    }

    @Test
    void getById_WhenNotFound_ReturnsEmpty() {
        when(departmentPersistencePort.getByDepartmentById(2L)).thenReturn(Optional.empty());

        Optional<DepartmentModel> result = departmentUseCase.getById(2L);

        assertTrue(result.isEmpty());
        verify(departmentPersistencePort).getByDepartmentById(2L);
    }
}


