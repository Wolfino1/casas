package com.casas.common.configurations.beans;


import com.casas.casas.domain.ports.in.CategoryServicePort;
import com.casas.casas.domain.ports.in.CityServicePort;
import com.casas.casas.domain.ports.in.DepartmentServicePort;
import com.casas.casas.domain.ports.in.LocationServicePort;
import com.casas.casas.domain.ports.out.CategoryPersistencePort;
import com.casas.casas.domain.ports.out.CityPersistencePort;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;
import com.casas.casas.domain.ports.out.LocationPersistencePort;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.usecases.CityUseCase;
import com.casas.casas.domain.usecases.DepartmentUseCase;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.infrastructure.adapters.persistence.mysql.CategoryPersistenceAdapter;
import com.casas.casas.infrastructure.adapters.persistence.mysql.CityPersistenceAdapter;
import com.casas.casas.infrastructure.adapters.persistence.mysql.DepartmentPersistenceAdapter;
import com.casas.casas.infrastructure.adapters.persistence.mysql.LocationPersistenceAdapter;
import com.casas.casas.infrastructure.mappers.*;
import com.casas.casas.infrastructure.repositories.mysql.CategoryRepository;
import com.casas.casas.infrastructure.repositories.mysql.CityRepository;
import com.casas.casas.infrastructure.repositories.mysql.DepartmentRepository;
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
    private final PageMapperInfra pageMapperInfra;
    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;

    @Bean
    public CategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper, pageMapperInfra);
    }
    @Bean
    public LocationServicePort locationServicePort() {
        return new LocationUseCase(locationPersistencePort(),cityUseCase());
    }

    @Bean
    public LocationPersistencePort locationPersistencePort() {
        return new LocationPersistenceAdapter(locationRepository, locationEntityMapper, pageMapperInfra);
    }
    @Bean
    public CityPersistencePort cityPersistencePort(){
        return new CityPersistenceAdapter(cityRepository, cityEntityMapper);
    }
    @Bean CityServicePort cityServicePort(){ return new CityUseCase(cityPersistencePort(),departmentUseCase());
    }
    @Bean
    public DepartmentPersistencePort departmentPersistencePort(){
        return new DepartmentPersistenceAdapter(departmentRepository,departmentEntityMapper);
    }
    @Bean
    public DepartmentServicePort departmentServicePort(){return new DepartmentUseCase(departmentPersistencePort());}

    @Bean
    public DepartmentUseCase departmentUseCase() {
        return new DepartmentUseCase(departmentPersistencePort());
    }

    @Bean
    public CityUseCase cityUseCase() {
        return new CityUseCase(cityPersistencePort(),departmentUseCase());
    }
}

