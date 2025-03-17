package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.utils.constants.DomainConstants;
import java.util.Objects;

    public class LocationModel {
        private Long id;
        private String name;
        private String city;
        private String cityDescription;
        private String department;
        private String departmentDescription;

        public LocationModel(Long id, String name, String city, String cityDescription, String department, String departmentDescription) {
            if (city.length() > 50 || department.length() > 50) throw new NameMaxSizeExceededException();
            if (cityDescription.length() > 120 || departmentDescription.length() > 120) throw new DescriptionMaxSizeExceededException();
            this.id = id;
            this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
            this.city = Objects.requireNonNull(city, DomainConstants.FIELD_CITY_NULL_MESSAGE);
            this.cityDescription = Objects.requireNonNull(cityDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
            this.department = Objects.requireNonNull(department, DomainConstants.FIELD_DEPARTMENT_NULL_MESSAGE);
            this.departmentDescription = Objects.requireNonNull(departmentDescription, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
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
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityDescription() {
            return cityDescription;
        }

        public void setCityDescription(String cityDescription) {
            this.cityDescription = cityDescription;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDepartmentDescription() {
            return departmentDescription;
        }

        public void setDepartmentDescription(String departmentDescription) {
            this.departmentDescription = departmentDescription;
        }
    }
