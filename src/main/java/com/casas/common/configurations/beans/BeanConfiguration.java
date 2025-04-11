package com.casas.common.configurations.beans;


import com.casas.casas.application.services.impl.HouseServiceImpl;
import com.casas.casas.domain.ports.in.*;
import com.casas.casas.domain.ports.out.*;
import com.casas.casas.domain.usecases.*;
import com.casas.casas.infrastructure.adapters.persistence.mysql.*;
import com.casas.casas.infrastructure.mappers.*;
import com.casas.casas.infrastructure.repositories.mysql.*;
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
    private final HouseEntityMapper houseEntityMapper;
    private final HouseRepository houseRepository;
    private final PubStatusRepository pubStatusRepository;
    private final PubStatusEntityMapper pubStatusEntityMapper;

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

    @Bean
    public HouseServicePort houseServicePort() {
        return new HouseUseCase(
                housePersistencePort(),
                locationUseCase(),
                categoryUseCase()
        );
    }

    @Bean
    public HousePersistencePort housePersistencePort() {
        return new HousePersistenceAdapter(houseRepository, houseEntityMapper,pageMapperInfra);
    }

    @Bean
    public PubStatusPersistencePort pubStatusPersistencePort() {
        return new PubStatusPersistenceAdapter(pubStatusRepository, pubStatusEntityMapper);
    }

    @Bean
    public PubStatusUseCase pubStatusUseCase() {
        return new PubStatusUseCase(pubStatusPersistencePort());
    }
    @Bean
    public LocationUseCase locationUseCase() {
        return new LocationUseCase(locationPersistencePort(),cityUseCase());
    }

    @Bean
    public CategoryUseCase categoryUseCase() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}

