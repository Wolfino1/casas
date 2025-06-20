package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.exceptions.NullException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;


public class CategoryUseCase implements CategoryServicePort {
    private final CategoryPersistencePort categoryPersistencePort;


    public CategoryUseCase(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(CategoryModel categoryModel) {
        Optional<CategoryModel> category = categoryPersistencePort.getByName(categoryModel.getName());
        if (category.isPresent()) {
            throw new CategoryAlreadyExistsException();
        }
        categoryPersistencePort.save(categoryModel);
    }

    @Override
    public PagedResult<CategoryModel> get(Integer page, Integer size, boolean orderAsc) {
        return categoryPersistencePort.get(page, size, orderAsc);
    }


    @Override
    public CategoryModel getById(Long id) {
        return categoryPersistencePort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DomainConstants.CATEGORY_DOES_NOT_EXIST));
    }


    @Override
    public Long getIdByName(String name) {
        CategoryModel model = categoryPersistencePort.findByName(name)
                .orElseThrow(() -> new NullException(DomainConstants.CATEGORY_DOES_NOT_EXIST));
        return model.getId();
    }

    @Override
    public String getNameById(Long idCategory) {
        CategoryModel model = getById(idCategory);
        return model.getName();
    }

}

