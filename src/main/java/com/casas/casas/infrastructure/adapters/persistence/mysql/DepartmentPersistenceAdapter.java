package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;
import com.casas.casas.infrastructure.mappers.DepartmentEntityMapper;
import com.casas.casas.infrastructure.repositories.mysql.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentPersistenceAdapter implements DepartmentPersistencePort {

    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;

    @Override
    public Optional<DepartmentModel> getByDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentEntityMapper::entityToModel);
    }

    @Override
    public List<DepartmentModel> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentEntityMapper::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentModel> getByDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .map(departmentEntityMapper::entityToModel);
    }
}
