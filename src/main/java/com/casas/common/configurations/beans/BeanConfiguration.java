package com.casas.common.configurations.beans;

import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.in.HomeServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.ports.out.HomePersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.usecases.HomeUseCase;
import com.casas.casas.infrastructure.adapters.persistence.mysql.CategoryPersistenceAdapter;
import com.casas.casas.infrastructure.adapters.persistence.mysql.HomePersistenceAdapter;
import com.casas.casas.infrastructure.mappers.CategoryEntityMapper;
import com.casas.casas.infrastructure.mappers.HomeEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
import com.casas.casas.infrastructure.repositories.mysql.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final HomeRepository homeRepository;
    private final HomeEntityMapper homeEntityMapper;


    @Bean
    public CategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public HomeServicePort homeServicePort() {
        return new HomeUseCase(homePersistencePort());
    }
    @Bean
    public HomePersistencePort homePersistencePort() {
        return new HomePersistenceAdapter(homeRepository, homeEntityMapper);
    }
}

