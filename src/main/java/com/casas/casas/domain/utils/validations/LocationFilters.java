package com.casas.casas.domain.utils.validations;

public class LocationFilters {
    private final String city;
    private final String department;

    public LocationFilters(String city, String department) {
        this.city = city;
        this.department = department;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }
}
