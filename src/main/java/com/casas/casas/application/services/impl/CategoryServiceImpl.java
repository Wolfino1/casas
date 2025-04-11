package com.casas.casas.application.services.impl;

import com.casas.casas.application.dto.request.SaveCategoryRequest;
import com.casas.casas.application.dto.response.CategoryResponse;
import com.casas.casas.application.dto.response.SaveCategoryResponse;
import com.casas.casas.application.mappers.CategoryDtoMapper;
import com.casas.casas.application.mappers.PageMapperApplication;
import com.casas.casas.application.services.CategoryService;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.common.configurations.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryServicePort categoryServicePort;
    private final CategoryDtoMapper categoryDtoMapper;
    private final CategoryPersistencePort categoryPersistencePort;
    private final PageMapperApplication pageMapper;

    @Override
    public SaveCategoryResponse save(SaveCategoryRequest request) {
        categoryServicePort.save(categoryDtoMapper.requestToModel(request));
        return new SaveCategoryResponse(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedResult<CategoryResponse> getCategories(Integer page, Integer size, boolean orderAsc) {
        PagedResult<CategoryModel> categoryModelPagedResult = categoryServicePort.get(page, size, orderAsc);
        List<CategoryResponse> content = categoryModelPagedResult.getContent().stream().map(categoryDtoMapper::modelToResponse)
                .toList();
        return pageMapper.fromPage(content, categoryModelPagedResult);
    }
    @Override
    public Long getIdByName(String name) {
        return categoryPersistencePort.getByName(name)
                .map(CategoryModel::getId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name));
    }
}
