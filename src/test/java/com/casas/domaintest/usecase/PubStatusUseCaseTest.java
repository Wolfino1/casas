package com.casas.domaintest.usecase;

import com.casas.casas.domain.model.PubStatusModel;
import com.casas.casas.domain.ports.out.PubStatusPersistencePort;
import com.casas.casas.domain.usecases.PubStatusUseCase;
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
class PubStatusUseCaseTest {

    @Mock
    private PubStatusPersistencePort pubStatusPersistencePort;

    @InjectMocks
    private PubStatusUseCase pubStatusUseCase;

    private PubStatusModel pubStatusModel;

    @BeforeEach
    void setUp() {
        pubStatusModel = new PubStatusModel();
    }

    @Test
    void getById_WhenExists_ReturnsOptional() {
        when(pubStatusPersistencePort.getPubStatusById(1L)).thenReturn(Optional.of(pubStatusModel));

        Optional<PubStatusModel> result = pubStatusUseCase.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(pubStatusModel, result.get());
        verify(pubStatusPersistencePort).getPubStatusById(1L);
    }

    @Test
    void getById_WhenNotFound_ReturnsEmpty() {
        when(pubStatusPersistencePort.getPubStatusById(2L)).thenReturn(Optional.empty());

        Optional<PubStatusModel> result = pubStatusUseCase.getById(2L);

        assertTrue(result.isEmpty());
        verify(pubStatusPersistencePort).getPubStatusById(2L);
    }
}
