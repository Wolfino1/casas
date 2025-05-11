package com.casas.casas.application.services.impl;
import com.casas.casas.application.services.DepartmentService;
import com.casas.casas.domain.model.DepartmentModel;
import com.casas.casas.domain.ports.out.DepartmentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentPersistencePort departmentPersistencePort;

    @Override
    public List<DepartmentModel> getAllDepartments() {
        return departmentPersistencePort.findAll();
    }

    @Override
    public DepartmentModel getDepartmentById(Long id) {
        return departmentPersistencePort.getByDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found for id: " + id));
    }
}
