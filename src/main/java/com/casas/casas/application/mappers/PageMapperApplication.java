package com.casas.casas.application.mappers;

import com.casas.casas.domain.utils.page.PagedResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageMapperApplication {
    public <T> PagedResult<T> fromPage(List<T> content, PagedResult<?> r) {
        return new PagedResult<>(
                content,
                r.getPage(),
                r.getSize(),
                r.getTotalElements()
        );
    }
}
