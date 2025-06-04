package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.CityModel;
import com.casas.casas.domain.ports.out.CityPersistencePort;
import com.casas.casas.infrastructure.entities.CityEntity;
import com.casas.casas.infrastructure.mappers.CityEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityPersistenceAdapter implements CityPersistencePort {

    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;

    @Override
    public void save(CityModel cityModel) {
        cityRepository.save(cityEntityMapper.toEntity(cityModel,cityModel.getDepartment()));
    }

    @Override
    public List<CityModel> findAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityEntityMapper::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CityModel> getByCityById(Long id) {
        return cityRepository.findById(id)
                .map(cityEntityMapper::entityToModel);
    }

    @Override
    public Optional<CityModel> getByCityByName(String name) {
        return cityRepository.findByName(name)
                .map(cityEntityMapper::entityToModel);
    }

    @Override
    public List<CityModel> findByDepartmentId(Long departmentId) {
        List<CityEntity> entities = cityRepository.findByDepartmentId(departmentId); // o findByDepartment_Id
        return entities.stream()
                .map(cityEntityMapper::entityToModel)
                .toList();
    }

}