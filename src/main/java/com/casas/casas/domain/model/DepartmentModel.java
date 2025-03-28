package com.casas.casas.domain.model;

import lombok.Data;

@Data
public class DepartmentModel {

    private Long id;
    private String name;
    private String description;

    public DepartmentModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}