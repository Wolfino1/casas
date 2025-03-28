package com.casas.casas.domain.model;

import lombok.Data;

@Data

public class CityModel {

    private Long id;
    private String name;
    private String description;
    private Long idDepartment;
    private DepartmentModel department;

    public CityModel(Long id, String name, String description, Long idDepartment, DepartmentModel department) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idDepartment = idDepartment;
        this.department = department;
    }
}
