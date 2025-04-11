package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.PubStatusModel;

import java.util.Optional;

public interface PubStatusServicePort {
    Optional<PubStatusModel> getById(Long id);
}
