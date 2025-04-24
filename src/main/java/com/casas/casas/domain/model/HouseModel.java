package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.ListingDateExceedTimeException;
import com.casas.casas.domain.utils.constants.DomainConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HouseModel {

    private Long id;
    private Long sellerId;
    private String name;
    private String address;
    private String description;
    private Long idCategory;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private int price;
    private Long idLocation;
    private LocalDate publishActivationDate;
    private Long idPubStatus;
    private LocalDateTime publishDate;

    public HouseModel(Long id, Long sellerId, String name, String address, String description, Long idCategory, int numberOfRooms, int numberOfBathrooms,
                      int price, Long idLocation, LocalDate publishActivationDate, Long idPubStatus, LocalDateTime publishDate){
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        setAddress(address);
        this.description = description;
        this.idCategory = idCategory;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.price = price;
        this.idLocation = idLocation;
        setPublishActivationDate (publishActivationDate);
        this.idPubStatus = idPubStatus;
        this.publishDate = publishDate;
    }
    public HouseModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(DomainConstants.FIELD_ADDRESS_NULL_MESSAGE);
        }
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long     getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public LocalDate getPublishActivationDate() {
        return publishActivationDate;
    }

    public void setPublishActivationDate(LocalDate publishActivationDate) {
        LocalDate maxListingDate = LocalDate.now().plusMonths(DomainConstants.MONTHS_TO_ADD);

        if (publishActivationDate.isAfter(maxListingDate)) {
            throw new ListingDateExceedTimeException(DomainConstants.LISTING_DATE_IS_IN_MORE_THAN_ONE_MONTH);
        }

        this.publishActivationDate = publishActivationDate;
    }

    public Long getIdPubStatus() {
        return idPubStatus;
    }

    public void setIdPubStatus(Long idPubStatus) {
        this.idPubStatus = idPubStatus;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }
}
