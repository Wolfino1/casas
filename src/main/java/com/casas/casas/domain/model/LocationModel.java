package com.casas.casas.domain.model;

import lombok.Builder;

@Builder
public class LocationModel {
    private Long id;
    private String name;
    private Long idCity;
    private CityModel city;


    public LocationModel(Long id, String name, Long idCity, CityModel city) {
        this.id = id;
        setName(name);
        setIdCity(idCity);
        setCity(city);
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

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public Long getIdCity() {
        return idCity; }

    public void setIdCity(Long idCity) {
        this.idCity = idCity; }
}
