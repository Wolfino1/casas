package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.CityPersistencePort;
import com.casas.casas.domain.usecases.CityUseCase;
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
class CityUseCaseTest {

    @Mock
    private CityPersistencePort cityPersistencePort;

    @Mock
    private DepartmentUseCase departmentUseCase;

    @InjectMocks
    private CityUseCase cityUseCase;

    private final Long departmentId = 1L;

    @Test
    void save_WhenDepartmentExists_SavesCity() {
        CityModel cityModel = new CityModel(1L, "Bucaramanga", "Ciudad bonita", departmentId, null);
        DepartmentModel departmentModel = new DepartmentModel(departmentId, "Santander", "Región cafetera");

        when(departmentUseCase.getById(departmentId)).thenReturn(Optional.of(departmentModel));

        cityUseCase.save(cityModel);

        assertEquals(departmentModel, cityModel.getDepartment());
        verify(cityPersistencePort).save(cityModel);
    }

    @Test
    void getById_WhenExists_ReturnsCityModel() {
        CityModel expected = new CityModel(1L, "Giron", "Colonial", departmentId, null);
        when(cityPersistencePort.getByCityById(1L)).thenReturn(Optional.of(expected));

        Optional<CityModel> result = cityUseCase.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }
}
