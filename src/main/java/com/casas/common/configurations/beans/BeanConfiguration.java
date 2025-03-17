package com.casas.common.configurations.beans;

import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.infrastructure.adapters.persistence.mysql.CategoryPersistenceAdapter;
import com.casas.casas.infrastructure.adapters.persistence.mysql.LocationPersistenceAdapter;
import com.casas.casas.infrastructure.mappers.CategoryEntityMapper;
import com.casas.casas.infrastructure.mappers.LocationEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
import com.casas.casas.infrastructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;


    @Bean
    public CategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public LocationServicePort locationServicePort() {
        return new LocationUseCase(locationPersistencePort());
    }

    @Bean
    public LocationPersistencePort locationPersistencePort() {
        return new LocationPersistenceAdapter(locationRepository, locationEntityMapper);
    }
}

