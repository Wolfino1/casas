package com.casas.common.configurations.beans;


import com.casas.casas.domain.ports.in.*;
import com.casas.casas.domain.ports.out.*;
import com.casas.casas.domain.usecases.*;
import com.casas.casas.infrastructure.adapters.persistence.mysql.*;
import com.casas.casas.infrastructure.mappers.*;
import com.casas.casas.infrastructure.repositories.mysql.*;
import com.casas.casas.infrastructure.security.JwtAuthenticationFilter;
import com.casas.casas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
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
    private final JwtUtil jwtUtil;


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
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/house/**").permitAll()
                        // aquí tus otras reglas, p.ej.
                        .requestMatchers(HttpMethod.POST, "/api/v1/house").hasRole("SELLER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}



