package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.CategoryAlreadyExistsException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import org.springframework.data.domain.Page;

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
    public Page<CategoryModel> get(Integer page, Integer size, boolean orderAsc) {
        return categoryPersistencePort.get(page, size, orderAsc);
    }

}

