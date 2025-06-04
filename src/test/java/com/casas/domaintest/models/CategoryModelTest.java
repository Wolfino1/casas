package com.casas.domaintest.models;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryModelTest {

    @Test
    void constructor_SetsFields_WhenValid() {
        CategoryModel cat = new CategoryModel(1L, "Nombre", "Descripción válida");
        assertEquals(1L, cat.getId());
        assertEquals("Nombre", cat.getName());
        assertEquals("Descripción válida", cat.getDescription());
    }


    @Test
    void setName_ThrowsEmptyException_WhenNameIsEmptyOrBlank() {
        CategoryModel cat = new CategoryModel(1L, "X", "Y");
        EmptyException ex1 = assertThrows(
                EmptyException.class,
                () -> cat.setName("")
        );
        assertEquals(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, ex1.getMessage());

        EmptyException ex2 = assertThrows(
                EmptyException.class,
                () -> cat.setName("   ")
        );
        assertEquals(DomainConstants.FIELD_NAME_EMPTY_MESSAGE, ex2.getMessage());
    }

    @Test
    void setName_ThrowsNameMaxSizeExceededException_WhenNameTooLong() {
        String longName = "A".repeat(51);
        CategoryModel cat = new CategoryModel(1L, "X", "Y");
        assertThrows(NameMaxSizeExceededException.class, () -> cat.setName(longName));
    }

    @Test
    void setName_AcceptsValidName() {
        CategoryModel cat = new CategoryModel(1L, "Inicial", "Desc");
        cat.setName("Nuevo Nombre");
        assertEquals("Nuevo Nombre", cat.getName());
    }


    @Test
    void setDescription_ThrowsEmptyException_WhenDescriptionIsEmptyOrBlank() {
        CategoryModel cat = new CategoryModel(1L, "X", "Y");
        EmptyException ex1 = assertThrows(
                EmptyException.class,
                () -> cat.setDescription("")
        );
        assertEquals(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE, ex1.getMessage());

        EmptyException ex2 = assertThrows(
                EmptyException.class,
                () -> cat.setDescription("   ")
        );
        assertEquals(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE, ex2.getMessage());
    }

    @Test
    void setDescription_ThrowsDescriptionMaxSizeExceededException_WhenDescriptionTooLong() {
        String longDesc = "D".repeat(91);
        CategoryModel cat = new CategoryModel(1L, "X", "Y");
        assertThrows(DescriptionMaxSizeExceededException.class, () -> cat.setDescription(longDesc));
    }

    @Test
    void setDescription_AcceptsValidDescription() {
        CategoryModel cat = new CategoryModel(1L, "X", "Inicial");
        cat.setDescription("Nueva descripción válida");
        assertEquals("Nueva descripción válida", cat.getDescription());
    }
}