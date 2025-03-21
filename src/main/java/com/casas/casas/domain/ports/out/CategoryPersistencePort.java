package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CategoryModel;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    Optional<CategoryModel> getByName(String categoryName);
    Page<CategoryModel> get(Integer page, Integer size, boolean orderAsc);
}

