package com.casas.casas.domain.utils.validations;

public class LocationService {

    public LocationFilters validateFilters(String city, String department) {
        String validCity = (city != null && !city.trim().isEmpty()) ? city.trim() : null;
        String validDepartment = (department != null && !department.trim().isEmpty()) ? department.trim() : null;

        return new LocationFilters(validCity, validDepartment);
    }
}