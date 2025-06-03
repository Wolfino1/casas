package com.casas.casas.application.dto.request;

import java.time.LocalDate;

public record SaveHouseRequest(
        String name,
        String address,
        String description,
        Long idCategory,
        int numberOfRooms,
        int numberOfBathrooms,
        int price,
        Long idLocation,
        LocalDate publishActivationDate,
        Long sellerId
) { }