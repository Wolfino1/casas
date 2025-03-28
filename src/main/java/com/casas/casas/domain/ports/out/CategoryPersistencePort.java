package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.utils.page.PagedResult;
import java.util.Optional;


public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    Optional<CategoryModel> getByName(String categoryName);
    PagedResult<CategoryModel> get(Integer page, Integer size, boolean orderAsc);
}

