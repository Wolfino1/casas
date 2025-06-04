package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.exceptions.NullException;
import com.casas.casas.domain.utils.constants.DomainConstants;

public class CategoryModel {
    private Long id;
    private String name;
    private String description;

    public CategoryModel(Long id, String name, String description) {

        this.id = id;
        setName(name);
        setDescription(description);
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
        if (name == null) {
            throw new NullException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if (name.trim().isEmpty()) {
            throw new EmptyException(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
        }
        if (name.length() > 50) {
            throw new NameMaxSizeExceededException();
        }
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new NullException(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if (description.trim().isEmpty()) {
            throw new EmptyException(DomainConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
        }
        if (description.length() > 90) {
            throw new DescriptionMaxSizeExceededException();
        }
        this.description = description;
    }
}
