package com.casas.casas.application.dto.request;

public record SaveCityRequest(String name, String description, Long idDepartment, String nameDepartment) {
}