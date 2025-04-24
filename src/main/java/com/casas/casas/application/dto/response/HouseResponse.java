package com.casas.casas.application.dto.response;


public record HouseResponse(
        Long id,
        Long sellerId,
        String category,
        int numberOfRooms,
        int numberOfBathrooms,
        int priceMin,
        int priceMax,
        LocationResponse location
) {}
