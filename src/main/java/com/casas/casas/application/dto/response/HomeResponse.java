package com.casas.casas.application.dto.response;

import com.casas.casas.infrastructure.entities.CategoryEntity;

public record HomeResponse(Long id, String name, String description, CategoryEntity category){
}
