package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.CategoryModel;
import org.springframework.data.domain.Page;

public interface CategoryServicePort {
    void save(CategoryModel categoryModel);
    Page<CategoryModel> get(Integer page, Integer size, boolean orderAsc);
    }
