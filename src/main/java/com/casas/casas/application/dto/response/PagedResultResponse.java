package com.casas.casas.application.dto.response;

import java.util.List;

public record PagedResultResponse<T>(
        List<T> content,
        int page,
        int size,
        boolean orderAsc,
        long totalElements,
        int totalPages
) {}