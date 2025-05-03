package com.casas.casas.application.services;

import com.casas.casas.domain.model.DepartmentModel;

import java.util.List;

public interface DepartmentService {
    List<DepartmentModel> getAllDepartments();
    DepartmentModel getDepartmentById(Long id);


}
