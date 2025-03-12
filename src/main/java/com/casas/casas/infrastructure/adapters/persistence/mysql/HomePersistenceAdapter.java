package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.HomeModel;
import com.casas.casas.domain.ports.out.HomePersistencePort;
import com.casas.casas.infrastructure.mappers.HomeEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.HomeRepository;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HomePersistenceAdapter implements HomePersistencePort {
    private final HomeRepository homeRepository;
    private final HomeEntityMapper homeEntityMapper;

    @Override
    public void save(HomeModel homeModel) {
        homeRepository.save(homeEntityMapper.modelToEntity(homeModel));
    }

    @Override
    public HomeModel getByName(String homeName) {
        return homeEntityMapper.entityToModel(homeRepository.findByName(homeName).orElse(null));
    }

    @Override
    public List<HomeModel> get(Integer page, Integer size, boolean orderAsc) {
        Pageable pagination;
        if (orderAsc) pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
        else pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
        return homeEntityMapper.entityListToModelList(homeRepository.findAll(pagination).getContent());
    }
}