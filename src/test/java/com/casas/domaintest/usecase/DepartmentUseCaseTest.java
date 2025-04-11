package com.casas.domaintest.usecase;

import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;
import com.casas.casas.domain.usecases.DepartmentUseCase;
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

    @Test
    void getById_WhenExists_ReturnsDepartment() {
        Long id = 1L;
        DepartmentModel expected = new DepartmentModel(id, "Santander", "Tierra de hormigas culonas");
        when(departmentPersistencePort.getByDepartmentById(id)).thenReturn(Optional.of(expected));

        Optional<DepartmentModel> result = departmentUseCase.getById(id);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(departmentPersistencePort).getByDepartmentById(id);
    }
}

