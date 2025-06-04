package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.CityPersistencePort;
import com.casas.casas.domain.usecases.CityUseCase;
import com.casas.casas.domain.usecases.DepartmentUseCase;
import com.casas.casas.domain.utils.constants.DomainConstants;
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
class CityUseCaseTest {

    @Mock
    private CityPersistencePort cityPersistencePort;

    @Mock
    private DepartmentUseCase departmentUseCase;

    @InjectMocks
    private CityUseCase cityUseCase;

    private DepartmentModel departmentModel;
    private CityModel cityModel;

    @BeforeEach
    void setUp() {
        departmentModel = new DepartmentModel(1L,"Santander",":D");
        cityModel = new CityModel(1L, "Bucaramanga", ":D",1L, null);
    }

    @Test
    void save_validCity_savesSuccessfully() {
        when(departmentUseCase.getById(1L)).thenReturn(Optional.of(departmentModel));

        assertDoesNotThrow(() -> cityUseCase.save(cityModel));

        verify(cityPersistencePort).save(cityModel);
        assertSame(departmentModel, cityModel.getDepartment());
    }

    @Test
    void save_invalidDepartment_throwsException() {
        when(departmentUseCase.getById(1L)).thenReturn(Optional.empty());

        EmptyException ex = assertThrows(
                EmptyException.class,
                () -> cityUseCase.save(cityModel)
        );
        assertEquals(DomainConstants.DEPARTMENT_DOES_NOT_EXIST, ex.getMessage());

        verify(cityPersistencePort, never()).save(any());
    }

    @Test
    void getById_WhenExists_ReturnsOptional() {
        when(cityPersistencePort.getByCityById(1L)).thenReturn(Optional.of(cityModel));

        Optional<CityModel> result = cityUseCase.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(cityModel, result.get());
        verify(cityPersistencePort).getByCityById(1L);
    }

    @Test
    void getById_WhenNotFound_ReturnsEmpty() {
        when(cityPersistencePort.getByCityById(2L)).thenReturn(Optional.empty());

        Optional<CityModel> result = cityUseCase.getById(2L);

        assertTrue(result.isEmpty());
        verify(cityPersistencePort).getByCityById(2L);
    }
}
