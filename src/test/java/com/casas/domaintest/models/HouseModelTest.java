package com.casas.domaintest.models;

import com.casas.casas.domain.exceptions.ListingDateExceedTimeException;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HouseModelTest {

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        HouseModel house = new HouseModel();

        house.setId(1L);
        house.setName("Casa de la abuela");
        house.setAddress("Calle 10 # 5-23");
        house.setDescription("Muy acogedora");
        house.setIdCategory(2L);
        house.setNumberOfRooms(3);
        house.setNumberOfBathrooms(2);
        house.setPrice(450000000);
        house.setIdLocation(4L);
        house.setPublishActivationDate(LocalDate.now());
        house.setPublishDate(LocalDateTime.now());
        house.setIdPubStatus(1L);

        assertEquals(1L, house.getId());
        assertEquals("Casa de la abuela", house.getName());
        assertEquals("Calle 10 # 5-23", house.getAddress());
        assertEquals("Muy acogedora", house.getDescription());
        assertEquals(2L, house.getIdCategory());
        assertEquals(3, house.getNumberOfRooms());
        assertEquals(2, house.getNumberOfBathrooms());
        assertEquals(450000000, house.getPrice());
        assertEquals(4L, house.getIdLocation());
        assertNotNull(house.getPublishActivationDate());
        assertNotNull(house.getPublishDate());
        assertEquals(1L, house.getIdPubStatus());
    }

    @Test
    void setPublishActivationDate_ShouldThrowException_WhenDateExceedsLimit() {
        HouseModel house = new HouseModel();
        LocalDate invalidDate = LocalDate.now().plusMonths(DomainConstants.MONTHS_TO_ADD + 1);

        ListingDateExceedTimeException ex = assertThrows(ListingDateExceedTimeException.class, () ->
                house.setPublishActivationDate(invalidDate));

        assertEquals(DomainConstants.LISTING_DATE_IS_IN_MORE_THAN_ONE_MONTH, ex.getMessage());
    }

    @Test
    void setName_ShouldThrowException_WhenNameIsNullOrEmpty() {
        HouseModel house = new HouseModel();

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
                house.setName(null));
        assertEquals(DomainConstants.FIELD_NAME_NULL_MESSAGE, ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
                house.setName(" "));
        assertEquals(DomainConstants.FIELD_NAME_NULL_MESSAGE, ex2.getMessage());
    }
}
