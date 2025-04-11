package com.casas.domaintest.usecase;

import com.casas.casas.domain.model.PubStatusModel;
import com.casas.casas.domain.ports.out.PubStatusPersistencePort;
import com.casas.casas.domain.usecases.PubStatusUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PubStatusUseCaseTest {

    @Mock
    private PubStatusPersistencePort pubStatusPersistencePort;

    @InjectMocks
    private PubStatusUseCase pubStatusUseCase;

    @Test
    void getById_WhenExists_ReturnsPubStatus() {
        Long id = 1L;
        PubStatusModel expected = new PubStatusModel();

        when(pubStatusPersistencePort.getPubStatusById(id)).thenReturn(Optional.of(expected));

        Optional<PubStatusModel> result = pubStatusUseCase.getById(id);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(pubStatusPersistencePort).getPubStatusById(id);
    }
}
