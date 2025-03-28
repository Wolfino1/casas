package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.exceptions.NullException;
import com.casas.casas.domain.utils.constants.DomainConstants;
import lombok.Builder;

import java.util.Objects;
@Builder
public class CategoryModel {
    private Long id;
    private String name;
    private String description;
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";



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
        if (name != null) {
            if (name.trim().isEmpty()) throw new EmptyException(NAME);
            if (name.length() > 50) throw new NameMaxSizeExceededException();
            this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        else throw new NullException("Name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            if (description.trim().isEmpty()) throw new EmptyException(DESCRIPTION);
            if (description.length() > 90) throw new DescriptionMaxSizeExceededException();
            this.description = Objects.requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
    }
}
