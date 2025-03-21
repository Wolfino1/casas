package com.casas.casas.infrastructure.entities;

import com.casas.casas.domain.model.LocationModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.casas.casas.domain.model.LocationModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String cityDescription;
    private String department;
    private String departmentDescription;

    public LocationModel toModel() {
        return LocationModel.builder()
                .id(this.id)
                .name(this.name != null ? this.name : "")
                .city(this.city != null ? this.city : "")
                .cityDescription(this.cityDescription != null ? this.cityDescription : "")
                .department(this.department != null ? this.department : "")
                .departmentDescription(this.departmentDescription != null ? this.departmentDescription : "")
                .build();
    }
}
