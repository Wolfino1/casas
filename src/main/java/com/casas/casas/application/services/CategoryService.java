package com.casas.casas.application.services;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.domain.utils.page.PagedResult;

public interface CategoryService {
    SaveCategoryResponse save(SaveCategoryRequest request);
    PagedResult<CategoryResponse> getCategories(Integer page, Integer size, boolean orderAsc);
    Long getIdByName(String name);
    String getNameById(Long idCategory);



}



