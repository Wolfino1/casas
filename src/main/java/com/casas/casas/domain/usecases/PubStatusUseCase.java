package com.casas.casas.domain.usecases;

import com.casas.casas.domain.model.PubStatusModel;
import com.casas.casas.domain.ports.in.PubStatusServicePort;
import com.casas.casas.domain.ports.out.PubStatusPersistencePort;

import java.util.Optional;

public class PubStatusUseCase implements PubStatusServicePort {
    private final PubStatusPersistencePort pubStatusPersistencePort;

    public PubStatusUseCase(PubStatusPersistencePort pubStatusPersistencePort) {
        this.pubStatusPersistencePort = pubStatusPersistencePort;
    }

    @Override
    public Optional<PubStatusModel> getById(Long id) {
        return pubStatusPersistencePort.getPubStatusById(id);
    }
}
