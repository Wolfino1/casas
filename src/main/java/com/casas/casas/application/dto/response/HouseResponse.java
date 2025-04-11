package com.casas.casas.application.dto.response;


public record HouseResponse (Long id,
                             String Category,
                             int numberOfRooms,
                             int numberOfBathrooms,
                             int priceMin,
                             int priceMax,
                             LocationResponse Location){
}
