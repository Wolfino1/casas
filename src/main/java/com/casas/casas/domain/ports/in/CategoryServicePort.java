package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.utils.page.PagedResult;

public interface CategoryServicePort {
    void save(CategoryModel categoryModel);
    PagedResult<CategoryModel> get(Integer page, Integer size, boolean orderAsc);
    CategoryModel getById(Long id);
}
