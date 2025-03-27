package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.exceptions.NullException;
import com.casas.casas.domain.utils.constants.DomainConstants;
import lombok.Builder;

import java.util.Objects;

@Builder
public class LocationModel {
    private Long id;
    private String name;
    private String city;
    private String cityDescription;
    private String department;
    private String departmentDescription;

    private static final String CITYDESCRIPTION = "City description";
    private static final String CITY = "City";
    private static final String DEPARTMENT = "Department";
    private static final String DEPARTMENTDESCRIPTION = "Department description";

    public LocationModel(Long id, String name, String city, String cityDescription, String department, String departmentDescription) {
        this.id = id;
        setName(name);
        setCity(city);
        setCityDescription(cityDescription);
        setDepartment(department);
        setDepartmentDescription(departmentDescription);
    }

    public LocationModel(Long id, String city, String department) {
        this.id = id;
        this.city = city;
        this.department = department;
    }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
        this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
        if (city != null){
            if (city.trim().isEmpty()) throw new EmptyException(CITY);
            if (city.length() > 50) throw new NameMaxSizeExceededException();
            this.city = Objects.requireNonNull(city, DomainConstants.FIELD_CITY_NULL_MESSAGE);
        }
       else
           throw new NullException(CITY);
        }


        public String getCityDescription() {
            return cityDescription;
        }

        public void setCityDescription(String cityDescription) {
            if (cityDescription != null) {
                if (cityDescription.trim().isEmpty()) throw new EmptyException(CITYDESCRIPTION);
                if (cityDescription.length() > 120) throw new DescriptionMaxSizeExceededException();
                this.cityDescription = Objects.requireNonNull(cityDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
            }
            else
                throw new NullException(CITYDESCRIPTION);
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            if (department != null) {
                if (department.trim().isEmpty()) throw new EmptyException(DEPARTMENT);
                if (department.length() > 50) throw new NameMaxSizeExceededException();
                this.department = Objects.requireNonNull(department, DomainConstants.FIELD_DEPARTMENT_NULL_MESSAGE);
            }
            else
                throw new NullException(DEPARTMENT);
        }
        public String getDepartmentDescription() {
            return departmentDescription;
        }

        public void setDepartmentDescription(String departmentDescription) {
            if (departmentDescription != null) {
                if (departmentDescription.trim().isEmpty()) throw new EmptyException(DEPARTMENTDESCRIPTION);
                if (departmentDescription.length() > 120) throw new DescriptionMaxSizeExceededException();
                this.departmentDescription = Objects.requireNonNull(departmentDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
            }
            else
                throw new NullException(CITYDESCRIPTION);
        }

}
