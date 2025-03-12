package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.infrastructure.mappers.CategoryEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
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
public class CategoryPersistenceAdapter implements CategoryPersistencePort {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void save(CategoryModel categoryModel) {
        categoryRepository.save(categoryEntityMapper.modelToEntity(categoryModel));
    }

    @Override
    public CategoryModel getByName(String categoryName) {
        return categoryEntityMapper.entityToModel(categoryRepository.findByName(categoryName).orElse(null));
    }

    @Override
    public List<CategoryModel> get(Integer page, Integer size, boolean orderAsc) {
        Pageable pagination;
        if (orderAsc) pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
        else pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
        return categoryEntityMapper.entityListToModelList(categoryRepository.findAll(pagination).getContent());
    }
    @Override
    public List<CategoryModel> getFilters(Integer page, Integer size, String name, String description, boolean orderAsc) {
        if (name != null) {
            return List.of(categoryEntityMapper.entityToModel(categoryRepository.findByName(name).orElse(null)));
        } else if (description != null) {
            Pageable pagination;
            if (orderAsc) pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).ascending());
            else pagination = PageRequest.of(page, size, Sort.by(Constants.PAGEABLE_FIELD_NAME).descending());
            return categoryEntityMapper.entityListToModelList(categoryRepository.findByDescriptionContaining(description, pagination)
                    .getContent());
        } else {
            return get(page, size, orderAsc);
        }
    }
}


