package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.entities.CategoryEntity;
import com.casas.casas.infrastructure.mappers.CategoryEntityMapper;
import com.casas.casas.infrastructure.mappers.PageMapperInfra;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryPersistencePort {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final PageMapperInfra pageMapperInfra;

    @Override
    public void save(CategoryModel categoryModel) {
        categoryRepository.save(categoryEntityMapper.modelToEntity(categoryModel));
    }

    @Override
    public Optional<CategoryModel> getByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .map(categoryEntityMapper::entityToModel);
    }

    @Override
    public PagedResult<CategoryModel> get(Integer page, Integer size, boolean orderAsc) {
        Pageable pagination;
        if (orderAsc) pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
        else pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
        return pageMapperInfra.fromPage(categoryRepository.findAll(pagination).map(categoryEntityMapper::entityToModel));
    }

    @Override
    public Optional<CategoryModel> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryEntityMapper::entityToModel);
    }
}


