package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.PubStatusModel;
import com.casas.casas.domain.ports.out.PubStatusPersistencePort;
import com.casas.casas.infrastructure.entities.PubStatusEntity;
import com.casas.casas.infrastructure.mappers.PubStatusEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.PubStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class PubStatusPersistenceAdapter implements PubStatusPersistencePort {

    private final PubStatusRepository pubStatusRepository;
    private final PubStatusEntityMapper pubStatusEntityMapper;

    @Override
    public Optional<PubStatusModel> getPubStatusById(Long id) {
        Optional<PubStatusEntity> pubStatusEntity = pubStatusRepository.findById(id);
        return pubStatusEntity.map(pubStatusEntityMapper::entityToModel);
    }
}
