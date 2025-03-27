package com.casas.casas.infrastructure.mappers;

import com.casas.casas.domain.utils.page.PagedResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapperInfra {
    public <T> PagedResult<T> fromPage(Page<T> page) {
        return new PagedResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
