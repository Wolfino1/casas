package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.PubStatusModel;

import java.util.Optional;

public interface PubStatusPersistencePort {
    Optional<PubStatusModel> getPubStatusById(Long id);
}
